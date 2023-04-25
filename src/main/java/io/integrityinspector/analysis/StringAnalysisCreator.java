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
    private final CountsExtractor countsExtractorImpl;
    private final BaseLineProjectLimiter baseLineProjectLimiterImpl;
    private final ProjectChecker<FileCheck, FileChecker<FileCheck>> stringProjectChecker;
    private final UniquenessPercentageCalculator uniquenessPercentageCalculatorImpl;


    public Analysis create(Project checkProject, List<Project> baselineProjects) {
        Analysis analysis = new Analysis();
        List<FileCheck> fileTreeChecks = projectAnalyzer.process(checkProject, baselineProjects);
        List<ProjectCount> countsPerProject = countsExtractorImpl.extractCountsPerProject(fileTreeChecks);
        List<Project> filteredBaselineProjects = baseLineProjectLimiterImpl.limitBaselineProjectList(countsPerProject, baselineProjects);

        List<FileCheck> filteredFileStringChecks = stringProjectChecker.checkProject(checkProject, filteredBaselineProjects);
        BigDecimal totalUniquenessPercentage = uniquenessPercentageCalculatorImpl.calculateTotalUniquenessPercentage(filteredFileStringChecks);

        analysis.setProjectChecks(filteredFileStringChecks);
        analysis.setCountPerProject(countsPerProject);
        analysis.setTotalUniquenessPercentage(totalUniquenessPercentage);

        return analysis;
    }


}
