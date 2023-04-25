package io.integrityinspector.analysis;

import io.integrityinspector.checker.FileChecker;
import io.integrityinspector.checker.ProjectChecker;
import io.integrityinspector.model.Analysis;
import io.integrityinspector.model.Project;
import io.integrityinspector.model.ProjectCount;
import io.integrityinspector.model.TreeCheckList;
import io.integrityinspector.model.filecheker.FileCheck;
import io.integrityinspector.model.filecheker.FileTreeCheck;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TreeAnalysisCreator implements AnalysisCreator {
    private final ProjectAnalyzer<FileTreeCheck> projectAnalyzer;
    private final CountsExtractor countsExtractorImpl;
    private final BaseLineProjectLimiter baseLineProjectLimiterImpl;
    private final ProjectChecker<FileCheck, FileChecker<FileCheck>> stringProjectChecker;
    private final UniquenessPercentageCalculator uniquenessPercentageCalculatorImpl;
    private final CodeTreeAnalysisExtractor codeTreeAnalysisExtractor;


    public Analysis create(Project checkProject, List<Project> baselineProjects) {
        Analysis analysis = new Analysis();
        List<FileTreeCheck> fileTreeChecks = projectAnalyzer.process(checkProject, baselineProjects);
        List<ProjectCount> countsPerProject = countsExtractorImpl.extractCountsPerProject(fileTreeChecks);
        List<Project> filteredBaselineProjects = baseLineProjectLimiterImpl.limitBaselineProjectList(countsPerProject, baselineProjects);

        List<FileCheck> filteredFileStringChecks = stringProjectChecker.checkProject(checkProject, filteredBaselineProjects);
        BigDecimal totalUniquenessPercentage = uniquenessPercentageCalculatorImpl.calculateTotalUniquenessPercentage(filteredFileStringChecks);

        List<TreeCheckList> limitedFileTreeChecks = codeTreeAnalysisExtractor.codeTreeCheck(fileTreeChecks);

        List<FileTreeCheck> finalFileTreeChecks = getFileTreeChecks(filteredFileStringChecks, limitedFileTreeChecks);
        analysis.setProjectChecks(finalFileTreeChecks);
        analysis.setCountPerProject(countsPerProject);
        analysis.setTotalUniquenessPercentage(totalUniquenessPercentage);

        return analysis;
    }

    private static List<FileTreeCheck> getFileTreeChecks(List<FileCheck> fileChecks, List<TreeCheckList> fileTreeChecks) {
        List<FileTreeCheck> finalFileTreeChecks = new ArrayList<>();
        for (FileCheck fileCheck : fileChecks) {
            for (TreeCheckList fileTreeCheck : fileTreeChecks) {
                if (fileTreeCheck.getFileName().equals(fileCheck.getCodeFileName())) {
                    finalFileTreeChecks.add(
                            new FileTreeCheck(
                                    fileCheck.getCodeFileName(),
                                    fileCheck.getCheckedLines(),
                                    fileCheck.getUniqueStringPresent(),
                                    fileTreeCheck.getTreeSimilarityList()
                            )
                    );
                }
            }
        }
        return finalFileTreeChecks;
    }


}
