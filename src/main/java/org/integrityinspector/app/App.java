package org.integrityinspector.app;

import com.beust.jcommander.JCommander;
import org.integrityinspector.analysis.AnalysisCreator;
import org.integrityinspector.config.AppConfig;
import org.integrityinspector.config.AppConfigReader;
import org.integrityinspector.jtwig.JtwigWriter;
import org.integrityinspector.model.Analysis;
import org.integrityinspector.model.Project;
import org.integrityinspector.parser.reader.project.ProjectParser;
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
        ProjectParser parser = new ProjectParser(config.getParseCodeConfig());
        LOG.info("Parsing check project ...");
        Project checkProject = parser.parseProject(checkFolder);
        LOG.info("Parsing baseline projects ...");
        List<Project> baselineProjects = parser.parseProjectListFromRootDir(parameters.getBaseLineProjectDir());
        AnalysisCreator analysisCreator = new AnalysisCreator(config.getAnalysisConfig());
        JtwigWriter writer = new JtwigWriter();
        LOG.info("Analysis in progress ...");
        Analysis analysis = analysisCreator.create(checkProject, baselineProjects);
        LOG.info("Generating report ...");
        writer.write(analysis, checkFolder.getName());

    }

    private static JCommander initAppParameters(AppParameters parameters) {
        return JCommander.newBuilder()
                .programName(PLAGIARISM_CHECKER)
                .addObject(parameters)
                .build();
    }


}
