package io.integrityinspector.analysis;

import io.integrityinspector.checker.FileChecker;
import io.integrityinspector.checker.ProjectChecker;
import io.integrityinspector.model.Analysis;
import io.integrityinspector.model.Project;
import io.integrityinspector.model.ProjectCount;
import io.integrityinspector.model.filecheker.FileCheck;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class StringAnalysisCreator implements AnalysisCreator {
    private final ProjectAnalyzer<FileCheck> projectAnalyzer;
    private final CountsExtractor countsExtractor;
    private final BaseLineProjectLimiter baseLineProjectLimiter;
    private final ProjectChecker<FileCheck, FileChecker<FileCheck>> stringProjectChecker;
    private final UniquenessPercentageCalculator uniquenessPercentageCalculator;
    private final ZzhUniquenessCoefficientCalculator zzhUniquenessCoefficientCalculator;


    public Analysis create(Project checkProject, List<Project> baselineProjects) {
        Analysis analysis = new Analysis();
        List<FileCheck> fileChecks = projectAnalyzer.process(checkProject, baselineProjects);
        List<ProjectCount> countsPerProject = countsExtractor.extractCountsPerProject(fileChecks);
        List<Project> filteredBaselineProjects = baseLineProjectLimiter.limitBaselineProjectList(countsPerProject, baselineProjects);

        List<FileCheck> filteredFileStringChecks = stringProjectChecker.checkProject(checkProject, filteredBaselineProjects);
        BigDecimal totalUniquenessPercentage = uniquenessPercentageCalculator.calculateTotalUniquenessPercentage(filteredFileStringChecks);
        BigDecimal zzhUniquenessCoefficient = zzhUniquenessCoefficientCalculator.calculateZzhUniquenessCoefficient(totalUniquenessPercentage, checkProject.getProjectLineCount());

        analysis.setProjectChecks(filteredFileStringChecks);
        analysis.setCountPerProject(countsPerProject);
        analysis.setTotalUniquenessPercentage(totalUniquenessPercentage);
        analysis.setZzhUniquenessCoefficient(zzhUniquenessCoefficient);

        return analysis;
    }


}
