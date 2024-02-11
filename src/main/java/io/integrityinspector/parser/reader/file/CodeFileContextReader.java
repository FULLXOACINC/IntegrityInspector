package io.integrityinspector.parser.reader.file;

import java.io.IOException;

public interface CodeFileContextReader {
    String readFileFullContext(String file) throws IOException;
}
