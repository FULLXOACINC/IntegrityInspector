package org.integrityinspector.analysis;

import lombok.AllArgsConstructor;
import org.integrityinspector.checker.FileChecker;
import org.integrityinspector.checker.ProjectChecker;
import org.integrityinspector.model.Analysis;
import org.integrityinspector.model.Project;
import org.integrityinspector.model.ProjectCount;
import org.integrityinspector.model.filecheker.FileCheck;

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
