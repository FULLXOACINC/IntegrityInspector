package org.plagiarism.parser;

import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Line;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjectCodeParser {
    public List<CodeFile> parseCode(File file) throws IOException {
        List<String> list = new ArrayList<>();
        List<CodeFile> result = new ArrayList<>();
        collectAllFiles(file, list);
        for (String element : list) {
            BufferedReader reader = new BufferedReader(new FileReader(element));
            CodeFile codeFile = new CodeFile(file.getName(), element, new ArrayList<>(), 0);
            long fileLaneCount = reader.lines().count();
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
                                    .replace(" ", "")
                                    .toLowerCase()
                                    .replace("_", "")
                                    .replace("\u003d", "")
                            )
                    );
                }
            }
            codeFile.setFileCount(Math.toIntExact(fileLaneCount));
            result.add(codeFile);
        }
        return result;
    }

    private void collectAllFiles(File dir, List<String> list) {
        for (final File fileEntry : dir.listFiles()) {
            if (fileEntry.isDirectory()) {
                collectAllFiles(fileEntry, list);
            } else {
                list.add(fileEntry.getAbsolutePath());
            }
        }
    }

    private boolean isStringNeedAddToCheckList(String str) {
        String filtered = str.replaceAll(" ", "").replaceAll("\t", "");
        if (filtered.startsWith("from") || filtered.startsWith("import") || filtered.startsWith("//") || filtered.startsWith("#") || filtered.startsWith("/*") || filtered.startsWith("*/")) {
            return false;
        }
        if (filtered.isEmpty()) {
            return false;
        }
        return !filtered
                .replace("{", "")
                .replace("}", "")
                .replace("(", "")
                .replace(")", "")
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .toLowerCase()
                .replace("_", "")
                .replace("\u003d", "")
                .isEmpty();
    }
}
