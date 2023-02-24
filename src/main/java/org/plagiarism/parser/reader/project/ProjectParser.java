package org.plagiarism.parser.reader.project;

import org.plagiarism.config.ParserConfig;
import org.plagiarism.model.Project;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectParser {
    private final ProjectCodeParser projectCodeParser;

    public ProjectParser(ParserConfig parseCodeConfig) {
        projectCodeParser = new ProjectCodeParser(parseCodeConfig);
    }

    public List<Project> parseProjectListFromRootDir(String dirPath) throws IOException {
        File root = new File(dirPath);
        List<Project> projectList = new ArrayList<>();
        for (File file : Objects.requireNonNull(root.listFiles())) {
            if (file.isDirectory()) {
                String name = file.getName();
                projectList.add(new Project(name, projectCodeParser.parseCode(file)));
            }
        }
        return projectList;
    }

    public Project parseProject(File file) throws IOException {
        return new Project(file.getName(), projectCodeParser.parseCode(file));
    }
}
