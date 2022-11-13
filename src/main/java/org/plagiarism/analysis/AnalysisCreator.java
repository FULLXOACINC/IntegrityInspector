package org.plagiarism.analysis;

import org.plagiarism.cheker.PlagiarismLineChecker;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.plagiarism.model.ProjectCount.PROJECT_COUNT_COMPARATOR;

public class AnalysisCreator {
    private AnalysisConfig config;
    private PlagiarismLineChecker lineChecker;
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final int SCALE = 2;

    public AnalysisCreator(AnalysisConfig config) {
        this.config = config;
        this.lineChecker = new PlagiarismLineChecker(config);
    }

    public Analysis create(Project checkProject, List<Project> baselineProjects) {
        Analysis analysis = new Analysis();
        List<FileCheck> fileChecks = checkProject(checkProject, baselineProjects);
        List<ProjectCount> countsPerProject = extractCountsPerProject(fileChecks, config.getProjectLimit());
        List<Project> filteredBaselineProjects = limitBaselineProjectList(countsPerProject, baselineProjects);
        List<FileCheck> filteredFileChecks = checkProject(checkProject, filteredBaselineProjects);
        analysis.setProjectChecks(filteredFileChecks);
        analysis.setCountPerProject(countsPerProject);
        return analysis;
    }

    private List<LineInfo> extractSimilarLines(List<Check> list) {
        return list
                .stream()
                .filter(distinctByKey(x -> x.getBaseline().getContent().trim() + x.getBaselineProject()))
                .sorted(Check.CHECK_COMPARATOR)
                .limit(config.getLineSimilarLimit())
                .map(x -> new LineInfo(x.getBaselineProject(), x.getBaselineCodeFile(), x.getLevenshteinDistance(), x.getCosineDistance(), x.getJaccardDistance(), x.getBaseline()))
                .sorted(LineInfo.LINE_INFO_COMPARATOR)
                .collect(Collectors.toList());
    }

    private List<FileCheck> checkProject(Project checkProject, List<Project> baselineProjects) {
        List<FileCheck> fileChecks = new ArrayList<>();
        for (CodeFile codeFile : checkProject.getCodeFileList()) {
            fileChecks.add(checkFile(codeFile, baselineProjects));
        }
        return fileChecks;
    }

    private FileCheck checkFile(CodeFile codeFile, List<Project> baselineProjects) {
        double plagiarismLineCount = 0.0;
        List<CheckLine> checkedLines = new ArrayList<>();
        for (Line checkedLine : codeFile.getCode()) {
            List<Check> checks = compareLineWithBaselineProjects(checkedLine, baselineProjects);
            List<LineInfo> similarLines = extractSimilarLines(checks);
            if (!similarLines.isEmpty()) {
                plagiarismLineCount++;
            }
            checkedLines.add(new CheckLine(checkedLine, similarLines));
        }

        BigDecimal uniquePresent = BigDecimal
                .valueOf(plagiarismLineCount)
                .multiply(ONE_HUNDRED)
                .divide(BigDecimal.valueOf(codeFile.getCode().size()), SCALE, RoundingMode.HALF_DOWN);
        return new FileCheck(codeFile.getSourceFile(), checkedLines, ONE_HUNDRED.subtract(uniquePresent));
    }

    private List<ProjectCount> extractCountsPerProject(List<FileCheck> fileChecks, int limit) {
        Map<String, Integer> map = extractMapWithCounts(fileChecks);
        List<ProjectCount> result = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result.add(new ProjectCount(entry.getKey(), entry.getValue()));
        }
        return result
                .stream()
                .sorted(PROJECT_COUNT_COMPARATOR)
                .limit(limit)
                .collect(Collectors.toList());
    }

    private Map<String, Integer> extractMapWithCounts(List<FileCheck> fileChecks) {
        Map<String, Integer> map = new HashMap<>();
        for (FileCheck file : fileChecks) {
            for (CheckLine checkLine : file.getCheckedLines()) {
                for (LineInfo similarLine : checkLine.getSimilarLines()) {
                    if (!map.containsKey(similarLine.getProject())) {
                        map.put(similarLine.getProject(), 1);
                    } else {
                        map.put(similarLine.getProject(), map.get(similarLine.getProject()) + 1);
                    }
                }
            }
        }
        return map;
    }

    private List<Project> limitBaselineProjectList(List<ProjectCount> countsPerProject, List<Project> baselineProjects) {
        Set<String> topNSimilarProjects = countsPerProject
                .stream()
                .map(ProjectCount::getName)
                .collect(Collectors.toSet());
        return baselineProjects
                .stream()
                .filter(x -> topNSimilarProjects.contains(x.getName()))
                .collect(Collectors.toList());
    }

    private List<Check> compareLineWithBaselineProjects(Line checkedLine, List<Project> baselineProjects) {
        List<Check> list = new ArrayList<>();
        for (Project baselineProject : baselineProjects) {
            for (CodeFile baselineCodeFile : baselineProject.getCodeFileList()) {
                list.addAll(lineChecker.check(checkedLine, baselineCodeFile, baselineProject.getName(), baselineCodeFile.getSourceFile()));
            }
        }
        return list;
    }

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
