package org.integrityinspector.parser.reader.project;

import org.integrityinspector.config.ParserConfig;
import org.integrityinspector.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectParserImpl implements ProjectParser {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectParserImpl.class);
    private final ProjectCodeParser projectCodeParser;

    public ProjectParserImpl(ParserConfig parseCodeConfig) {
        projectCodeParser = new ProjectCodeParser(parseCodeConfig);
    }

    @Override
    public List<Project> parseProjectListFromRootDir(String dirPath) throws IOException {
        LOG.info("Start parsing process of root dir: {}", dirPath);
        File root = new File(dirPath);
        List<Project> projectList = new ArrayList<>();
        for (File file : Objects.requireNonNull(root.listFiles())) {
            if (file.isDirectory()) {
                String name = file.getName();
                LOG.info("Find new project: {}", name);
                projectList.add(new Project(name, projectCodeParser.parseCode(file)));
            } else {
                LOG.info("[IGNORE] file isn't dir: {}", file.getName());
            }
        }
        return projectList;
    }

    @Override
    public Project parseProject(File file) throws IOException {
        LOG.info("Start parsing process of dir: {}", file.getName());
        return new Project(file.getName(), projectCodeParser.parseCode(file));
    }
}
