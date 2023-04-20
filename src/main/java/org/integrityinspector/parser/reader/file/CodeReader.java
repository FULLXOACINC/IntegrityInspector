package org.integrityinspector.parser.reader.file;

import org.integrityinspector.model.CodeFile;

import java.io.IOException;

public interface CodeReader {

    CodeFile read(String file) throws IOException;
}
