package org.plagiarism.parser.cleaner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum CodeReaderFactory {
    PY(new PythonReader()),
    DEFAULT(new DefaultReader());

    private final CodeReader reader;
    private static final Set<String> AVAILABLE_EXTENSIONS = new HashSet<>(Arrays.asList("PY"));

    CodeReaderFactory(CodeReader reader) {
        this.reader = reader;
    }

    public static CodeReader findCodeReader(String fileExtension) {
        if (AVAILABLE_EXTENSIONS.contains(fileExtension)) {
            return CodeReaderFactory.valueOf(fileExtension).reader;
        } else {
            return CodeReaderFactory.DEFAULT.reader;
        }

    }
}
