package org.plagiarism.app;

import com.beust.jcommander.JCommander;
import org.plagiarism.analysis.AnalysisCreator;
import org.plagiarism.config.AppConfig;
import org.plagiarism.config.AppConfigReader;
import org.plagiarism.jtwig.JtwigWriter;
import org.plagiarism.model.Analysis;
import org.plagiarism.model.Project;
import org.plagiarism.parser.reader.project.ProjectParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class App {

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
        List<Project> baselineProjects = parser.parseProjectListFromRootDir(parameters.getBaseLineProjectDir());
        Project checkProject = parser.parseProject(checkFolder);
        AnalysisCreator analysisCreator = new AnalysisCreator(config.getAnalysisConfig());
        JtwigWriter writer = new JtwigWriter();
        Analysis analysis = analysisCreator.create(checkProject, baselineProjects);
        writer.write(analysis, checkFolder.getName());

    }

    private static JCommander initAppParameters(AppParameters parameters) {
        return JCommander.newBuilder()
                .programName("PlagiarismChecker")
                .addObject(parameters)
                .build();
    }


}
