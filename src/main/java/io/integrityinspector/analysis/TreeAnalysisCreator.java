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
    private final CountsExtractor countsExtractor;
    private final BaseLineProjectLimiter baseLineProjectLimiter;
    private final ProjectChecker<FileCheck, FileChecker<FileCheck>> stringProjectChecker;
    private final UniquenessPercentageCalculator uniquenessPercentageCalculator;
    private final CodeTreeAnalysisExtractor codeTreeAnalysisExtractor;
    private final Zzh1UniquenessCoefficientCalculator zzh1UniquenessCoefficientCalculator;


    public Analysis create(Project checkProject, List<Project> baselineProjects) {
        Analysis analysis = new Analysis();
        List<FileTreeCheck> fileTreeChecks = projectAnalyzer.process(checkProject, baselineProjects);
        List<ProjectCount> countsPerProject = countsExtractor.extractCountsPerProject(fileTreeChecks);
        List<Project> filteredBaselineProjects = baseLineProjectLimiter.limitBaselineProjectList(countsPerProject, baselineProjects);

        List<FileCheck> filteredFileStringChecks = stringProjectChecker.checkProject(checkProject, filteredBaselineProjects);
        BigDecimal totalUniquenessPercentage = uniquenessPercentageCalculator.calculateTotalUniquenessPercentage(filteredFileStringChecks);
        BigDecimal zzhUniquenessCoefficient = zzh1UniquenessCoefficientCalculator.calculateZzh1UniquenessCoefficient(totalUniquenessPercentage, checkProject.getProjectLineCount());

        List<TreeCheckList> limitedFileTreeChecks = codeTreeAnalysisExtractor.codeTreeCheck(fileTreeChecks);

        List<FileTreeCheck> finalFileTreeChecks = getFileTreeChecks(filteredFileStringChecks, limitedFileTreeChecks);
        analysis.setProjectChecks(finalFileTreeChecks);
        analysis.setCountPerProject(countsPerProject);
        analysis.setTotalUniquenessPercentage(totalUniquenessPercentage);
        analysis.setZzh1UniquenessCoefficient(zzhUniquenessCoefficient);

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
