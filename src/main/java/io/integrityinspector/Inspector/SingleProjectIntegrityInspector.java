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

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SingleProjectIntegrityInspector implements IntegrityInspector {
    private static final Logger LOG = LoggerFactory.getLogger(SingleProjectIntegrityInspector.class);
    private final AppCoreComponents appCoreComponents;

    public SingleProjectIntegrityInspector(AppCoreComponents appCoreComponents) {
        this.appCoreComponents = appCoreComponents;
    }

    @Override
    public void process(AppParameters parameters) {
        try {
            File checkFolder = new File(parameters.getCheckingProject());

            ProjectParser parser = appCoreComponents.getProjectParser();
            LOG.info("Parsing check project ...");
            Project checkProject = parser.parseProject(checkFolder);
            LOG.info("Parsing baseline projects from dir " + parameters.getBaseLineProjectDir() + " ...");
            List<Project> baselineProjects = parser.parseProjectListFromRootDir(parameters.getBaseLineProjectDir());
            LOG.info("Analysis in progress ...");

            AnalysisCreator analysisCreator = appCoreComponents.getAnalysisCreator();
            Analysis analysis = analysisCreator.create(checkProject, baselineProjects);
            LOG.info("Generating report ...");

            AnalysisWriter analysisWriter = appCoreComponents.getAnalysisWriter();
            analysisWriter.write(analysis, checkFolder.getName(), appCoreComponents.getReportTemplate());
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
