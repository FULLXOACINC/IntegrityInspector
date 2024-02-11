package io.integrityinspector.parser.reader.project;

import io.integrityinspector.model.CodeFile;
import io.integrityinspector.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultProjectParser implements ProjectParser {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultProjectParser.class);
    private final ProjectCodeParser projectCodeParser;

    public DefaultProjectParser(ProjectCodeParser projectCodeParser) {
        this.projectCodeParser = projectCodeParser;
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
                projectList.add(createProject(file));
            } else {
                LOG.info("[IGNORE] file isn't dir: {}", file.getName());
            }
        }
        return projectList;
    }

    @Override
    public Project parseProject(File file) throws IOException {
        LOG.info("Start parsing process of dir: {}", file.getName());
        return createProject(file);
    }

    private Project createProject(File file) throws IOException {
        List<CodeFile> codeFileList = projectCodeParser.parseCode(file);
        int projectLineCount = codeFileList.stream()
                .map(CodeFile::getFileLineCount)
                .reduce(Integer::sum).orElse(1000);
        return new Project(file.getName(), codeFileList, projectLineCount);
    }
}
