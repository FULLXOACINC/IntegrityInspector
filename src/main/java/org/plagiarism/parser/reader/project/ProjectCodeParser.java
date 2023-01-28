package org.plagiarism.parser.reader.project;

import org.plagiarism.model.CodeFile;
import org.plagiarism.parser.reader.file.CodeReader;
import org.plagiarism.parser.reader.file.CodeReaderFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectCodeParser {
    public List<CodeFile> parseCode(File dir) throws IOException {
        List<String> files = new ArrayList<>();
        List<CodeFile> result = new ArrayList<>();
        collectAllFiles(dir, files);
        for (String element : files) {
            String[] arr = element.split("\\.");
            String fileExtension = arr[arr.length - 1].toUpperCase();
            CodeReader reader = CodeReaderFactory.findCodeReader(fileExtension);
            result.add(reader.read(element));
        }
        return result;
    }

    private void collectAllFiles(File dir, List<String> list) {
        for (File fileEntry : Objects.requireNonNull(dir.listFiles())) {
            if (fileEntry.isDirectory()) {
                collectAllFiles(fileEntry, list);
            } else {
                list.add(fileEntry.getAbsolutePath());
            }
        }
    }


}
