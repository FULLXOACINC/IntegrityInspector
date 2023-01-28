package org.plagiarism.parser.reader.file;

import org.plagiarism.model.CodeFile;

import java.io.IOException;

public interface CodeReader {

    CodeFile read(String file) throws IOException;
}
