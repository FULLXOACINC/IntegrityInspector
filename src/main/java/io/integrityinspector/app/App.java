package io.integrityinspector.app;

import com.beust.jcommander.JCommander;
import io.integrityinspector.analysis.AnalysisCreator;
import io.integrityinspector.config.AppConfig;
import io.integrityinspector.config.AppConfigReader;
import io.integrityinspector.jtwig.JtwigWriter;
import io.integrityinspector.model.Analysis;
import io.integrityinspector.model.Project;
import io.integrityinspector.parser.reader.project.ProjectParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class App {
    private static final Logger LOG = LoggerFactory.getLogger(App.class);

    public static final String PLAGIARISM_CHECKER = "IntegrityInspector";

    public static void main(String[] args) throws IOException {

        AppParameters parameters = new AppParameters();
        JCommander jCommander = initAppParameters(parameters);
        jCommander.parse(args);
        if (parameters.isHelp()) {
            jCommander.usage();
            return;
        }

        File checkFolder = new File(parameters.getCheckingProject());

        AppConfigReader configReader = new AppConfigReader();
        AppConfig config = configReader.readBasedOnParameters(parameters);

        AppCoreComponentsFactory appCoreComponentsFactory = new AppCoreComponentsFactory();
        AppCoreComponents appCoreComponents = appCoreComponentsFactory.createAppCoreComponents(config);

        ProjectParser parser = appCoreComponents.getProjectParser();
        LOG.info("Parsing check project ...");
        Project checkProject = parser.parseProject(checkFolder);
        LOG.info("Parsing baseline projects ...");
        List<Project> baselineProjects = parser.parseProjectListFromRootDir(parameters.getBaseLineProjectDir());
        LOG.info("Analysis in progress ...");

        AnalysisCreator analysisCreator = appCoreComponents.getAnalysisCreator();
        Analysis analysis = analysisCreator.create(checkProject, baselineProjects);
        LOG.info("Generating report ...");
        JtwigWriter writer = new JtwigWriter();
        writer.write(analysis, checkFolder.getName(), appCoreComponents.getReportTemplate());

    }

    private static JCommander initAppParameters(AppParameters parameters) {
        return JCommander.newBuilder()
                .programName(PLAGIARISM_CHECKER)
                .addObject(parameters)
                .build();
    }


}
