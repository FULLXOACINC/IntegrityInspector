package io.integrityinspector.parser.reader.file;

import io.integrityinspector.model.CodeFile;

import java.io.IOException;

public interface CodeReader {

    CodeFile read(String file) throws IOException;
}
