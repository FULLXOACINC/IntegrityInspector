package org.plagiarism.parser.cleaner;

import org.plagiarism.model.CodeFile;

import java.io.BufferedReader;
import java.io.IOException;

public interface CodeReader {

    CodeFile read(String pythonFile) throws IOException;
}
