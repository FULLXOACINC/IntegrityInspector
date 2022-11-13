package org.plagiarism.parser.cleaner;

import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Line;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PythonReader implements CodeReader {
    @Override
    public CodeFile read(String pythonFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pythonFile));
        CodeFile codeFile = new CodeFile(pythonFile, new ArrayList<>(), 0);
        int index = 0;
        while (true) {
            index++;
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            if (isStringNeedAddToCheckList(line)) {
                codeFile.addLine(
                        new Line(index, line, line
                                .toLowerCase()
                                .replace("(", "")
                                .replace(")", "")
                                .replace("[", "")
                                .replace("]", "")
                                .replace(" ", "")
                                .replace("_", "")
                                .replace("\u003d", "")
                        )
                );
            }
        }
        codeFile.setFileLineCount(Math.toIntExact(index - 1));
        return codeFile;
    }

    private boolean isStringNeedAddToCheckList(String str) {
        String filtered = str.replaceAll(" ", "").replaceAll("\t", "").trim();
        if (filtered.isEmpty()) {
            return false;
        }
        if (filtered.startsWith("from") || filtered.startsWith("import")  || filtered.startsWith("#") || filtered.startsWith("\"\"\"")) {
            return false;
        }
        return !filtered
                .toLowerCase()
                .replace("(", "")
                .replace(")", "")
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .replace("_", "")
                .replace("\u003d", "")
                .isEmpty();
    }
}
