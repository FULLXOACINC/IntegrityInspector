package org.integrityinspector.analysis;

import lombok.AllArgsConstructor;
import org.integrityinspector.checker.FileChecker;
import org.integrityinspector.checker.ProjectChecker;
import org.integrityinspector.model.Analysis;
import org.integrityinspector.model.Project;
import org.integrityinspector.model.ProjectCount;
import org.integrityinspector.model.TreeCheckList;
import org.integrityinspector.model.filecheker.FileCheck;
import org.integrityinspector.model.filecheker.FileTreeCheck;

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
