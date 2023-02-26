package org.plagiarism.analysis;

import lombok.AllArgsConstructor;
import org.plagiarism.checker.ProjectChecker;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static org.plagiarism.model.ProjectCount.PROJECT_COUNT_COMPARATOR;

@AllArgsConstructor
public class AnalysisCreator {
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal ZERO = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_DOWN);
    private static final int SCALE = 2;
    private final AnalysisConfig config;

    public Analysis create(Project checkProject, List<Project> baselineProjects) {
        Analysis analysis = new Analysis();
        MultithreadingProjectChecker multithreadingProjectChecker = new MultithreadingProjectChecker(config, config.getThreadsCount());
        ProjectChecker projectChecker = new ProjectChecker(config);

        List<FileCheck> fileChecks = multithreadingProjectChecker.process(checkProject, baselineProjects);
        List<ProjectCount> countsPerProject = extractCountsPerProject(fileChecks, config.getProjectLimit());
        List<Project> filteredBaselineProjects = limitBaselineProjectList(countsPerProject, baselineProjects);
        List<FileCheck> filteredFileChecks = projectChecker.checkProject(checkProject, filteredBaselineProjects);
        analysis.setProjectChecks(filteredFileChecks);
        analysis.setCountPerProject(countsPerProject);

        analysis.setTotalUniquenessPercentage(calculateTotalUniquenessPercentage(filteredFileChecks));
        return analysis;
    }

    private BigDecimal calculateTotalUniquenessPercentage(List<FileCheck> filteredFileChecks) {
        long checkedLineCount = filteredFileChecks
                .stream()
                .map(x -> x.getCheckedLines().size())
                .reduce(Integer::sum).orElse(0);
        long matchedLineCount = filteredFileChecks
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


}
