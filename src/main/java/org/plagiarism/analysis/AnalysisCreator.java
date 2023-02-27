package org.plagiarism.analysis;

import lombok.AllArgsConstructor;
import org.plagiarism.checker.FileStringChecker;
import org.plagiarism.checker.PlagiarismLineChecker;
import org.plagiarism.checker.ProjectChecker;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.*;
import org.plagiarism.model.filecheker.FileFullCheck;
import org.plagiarism.model.filecheker.FileStringCheck;
import org.plagiarism.model.filecheker.FileTreeCheck;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static org.plagiarism.model.ProjectCount.PROJECT_COUNT_COMPARATOR;
import static org.plagiarism.model.TreeSimilarity.TREE_SCORE_COMPARATOR;

@AllArgsConstructor
public class AnalysisCreator {
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal ZERO = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_DOWN);
    private static final int SCALE = 2;
    private final AnalysisConfig config;

    public Analysis create(Project checkProject, List<Project> baselineProjects) {
        Analysis analysis = new Analysis();
        MultithreadingProjectChecker multithreadingProjectChecker = new MultithreadingProjectChecker(config, config.getThreadsCount());
        List<FileFullCheck> fileFullChecks = multithreadingProjectChecker.process(checkProject, baselineProjects);
        List<ProjectCount> countsPerProject = extractCountsPerProject(fileFullChecks, config.getProjectLimit());
        List<Project> filteredBaselineProjects = limitBaselineProjectList(countsPerProject, baselineProjects);

        List<FileTreeCheck> projectTreeChecks = convertToFileTreeCheckList(fileFullChecks);
        List<FileTreeCheck> limitedProjectTreeChecks = limitTreeCheck(projectTreeChecks, config.getProjectLimit());


        List<FileStringCheck> filteredFileStringChecks = processFileStringChecks(checkProject, filteredBaselineProjects);
        BigDecimal totalUniquenessPercentage = calculateTotalUniquenessPercentage(filteredFileStringChecks);

        analysis.setProjectStringChecks(filteredFileStringChecks);
        analysis.setCountPerProject(countsPerProject);
        analysis.setTotalUniquenessPercentage(totalUniquenessPercentage);
        analysis.setProjectTreeChecks(limitedProjectTreeChecks);

        return analysis;
    }

    private List<FileStringCheck> processFileStringChecks(Project checkProject, List<Project> filteredBaselineProjects) {
        PlagiarismLineChecker plagiarismLineChecker = new PlagiarismLineChecker(config);
        FileStringChecker fileStringChecker = new FileStringChecker(plagiarismLineChecker, config);
        ProjectChecker<FileStringCheck, FileStringChecker> stringProjectChecker = new ProjectChecker<>(config, fileStringChecker);

        return stringProjectChecker.checkProject(checkProject, filteredBaselineProjects);
    }

    private BigDecimal calculateTotalUniquenessPercentage(List<FileStringCheck> filteredFileStringChecks) {
        long checkedLineCount = filteredFileStringChecks
                .stream()
                .map(x -> x.getCheckedLines().size())
                .reduce(Integer::sum).orElse(0);
        long matchedLineCount = filteredFileStringChecks
                .stream()
                .flatMap(x -> x.getCheckedLines().stream())
                .filter(x -> x.getSimilarLines().isEmpty())
                .count();
        if (checkedLineCount == 0 || matchedLineCount == 0) {
            return ZERO;
        }
        return BigDecimal
                .valueOf(matchedLineCount)
                .multiply(ONE_HUNDRED)
                .divide(BigDecimal.valueOf(checkedLineCount), SCALE, RoundingMode.HALF_DOWN);
    }


    private List<ProjectCount> extractCountsPerProject(List<FileFullCheck> fileFullChecks, int limit) {
        Map<String, Integer> map = extractMapWithCounts(fileFullChecks);
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

    private Map<String, Integer> extractMapWithCounts(List<FileFullCheck> fileFullChecks) {
        Map<String, Integer> map = new HashMap<>();
        for (FileFullCheck file : fileFullChecks) {
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

    private List<FileTreeCheck> convertToFileTreeCheckList(List<FileFullCheck> fileFullChecks) {
        return fileFullChecks
                .stream()
                .map(x -> new FileTreeCheck(x.getCodeFileName(), x.getCodeTreeSimilarityList()))
                .collect(Collectors.toList());
    }

    private List<FileTreeCheck> limitTreeCheck(List<FileTreeCheck> checks, int limit) {
        return checks
                .stream()
                .map(x -> new FileTreeCheck(
                        x.getCodeFileName(),
                        x.getCodeTreeSimilarityList()
                                .stream()
                                .sorted(TREE_SCORE_COMPARATOR)
                                .limit(limit)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

    }


}
