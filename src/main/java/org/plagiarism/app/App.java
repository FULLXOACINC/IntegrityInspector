package org.plagiarism.app;

import org.plagiarism.analysis.AnalysisCreator;
import org.plagiarism.config.AppConfig;
import org.plagiarism.config.AppConfigReader;
import org.plagiarism.jtwig.JtwigWriter;
import org.plagiarism.model.Project;
import org.plagiarism.parser.core.ProjectParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            throw new RuntimeException("Must provide the path to the project being checked and to the folder with the baselines");
        }

        File checkFolder = new File(args[0]);
        File folder = new File(args[1]);
        ProjectParser parser = new ProjectParser();
        AppConfigReader configReader = new AppConfigReader();
        AppConfig config = configReader.read();
        List<Project> baselineProjects = parser.parseProjectListFromRootDir(folder);
        Project checkProject = parser.parseProject(checkFolder);
        AnalysisCreator analysisCreator = new AnalysisCreator(config.getAnalysisConfig());
        JtwigWriter writer = new JtwigWriter();
        writer.write(analysisCreator.create(checkProject, baselineProjects), checkFolder.getName());

    }


}
