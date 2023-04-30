package io.integrityinspector.parser.reader.file;

import io.integrityinspector.model.CodeFile;

import java.io.IOException;

public interface CodeReader<T extends CodeFile> {
    T read(String file) throws IOException;

    T createCodeFile(String file, String fileContext);
}
