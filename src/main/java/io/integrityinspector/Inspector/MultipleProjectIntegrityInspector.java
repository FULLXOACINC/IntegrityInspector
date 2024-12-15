package io.integrityinspector.Inspector;

import io.integrityinspector.analysis.AnalysisCreator;
import io.integrityinspector.app.AppCoreComponents;
import io.integrityinspector.app.AppParameters;
import io.integrityinspector.model.Analysis;
import io.integrityinspector.model.Project;
import io.integrityinspector.parser.reader.project.ProjectParser;
import io.integrityinspector.write.core.AnalysisWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleProjectIntegrityInspector implements IntegrityInspector {
    private static final Logger LOG = LoggerFactory.getLogger(MultipleProjectIntegrityInspector.class);
    private final AppCoreComponents appCoreComponents;

    public MultipleProjectIntegrityInspector(AppCoreComponents appCoreComponents) {
        this.appCoreComponents = appCoreComponents;
    }

    @Override
    public void process(AppParameters parameters) {
        try {
            ProjectParser parser = appCoreComponents.getProjectParser();
            LOG.info("Parsing projects from dir " + parameters.getCheckingDirectory() + " ...");
            List<Project> projects = parser.parseProjectListFromRootDir(parameters.getCheckingDirectory());
            AnalysisWriter analysisWriter = appCoreComponents.getAnalysisWriter();
            AnalysisCreator analysisCreator = appCoreComponents.getAnalysisCreator();
            int maxUniquenessPercentageForCreatingReport = appCoreComponents
                    .getConfig()
                    .getMultipleProjectCheckConfig()
                    .getMaxUniquenessPercentageForCreatingReport();
            for (Project checkProject : projects) {
                String projectName = checkProject.getName();
                List<Project> baselineProjects = projects
                        .stream()
                        .filter(x -> !x.getName().equals(projectName))
                        .collect(Collectors.toList());
                Analysis analysis = analysisCreator.create(checkProject, baselineProjects);

                if (analysis.getTotalUniquenessPercentage()
                        .compareTo(BigDecimal.valueOf(maxUniquenessPercentageForCreatingReport)) > 0) {
                    LOG.info("Skipping based on maxUniquenessPercentageForCreatingReport(" + maxUniquenessPercentageForCreatingReport + "): " + analysis.getTotalUniquenessPercentage() + ", project:" + projectName);
                } else {
                    LOG.info("Generating report ...");
                    analysisWriter.write(analysis, projectName);
                }
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
