package io.integrityinspector.parser.reader.project;

import io.integrityinspector.model.Project;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ProjectParser {

    List<Project> parseProjectListFromRootDir(String dirPath) throws IOException;

    Project parseProject(File file) throws IOException;
}
