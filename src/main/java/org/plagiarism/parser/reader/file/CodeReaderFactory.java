package org.plagiarism.parser.reader.file;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum CodeReaderFactory {
    PY(new PythonReader()),
    JAVA(new JavaReader()),
    CPP(new CppReader()),
    C(new CppReader()),
    JS(new JsReader()),
    TS(new JsReader()),
    CS(new CSharpReader()),
    IPYNB(new IpynbReader()),
    DEFAULT(new DefaultReader());

    private final CodeReader reader;
    private static final Set<String> AVAILABLE_EXTENSIONS = new HashSet<>(
            Arrays.asList("PY", "JAVA", "CPP", "C", "JS", "TS", "CS", "IPYNB")
    );

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
