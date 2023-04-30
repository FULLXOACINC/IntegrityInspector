package io.integrityinspector.parser.reader.project;

import io.integrityinspector.model.CodeFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ProjectCodeParser {
    List<CodeFile> parseCode(File dir) throws IOException;
}
