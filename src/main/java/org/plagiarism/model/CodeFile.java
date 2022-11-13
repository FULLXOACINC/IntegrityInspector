package org.plagiarism.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CodeFile {
    private String project;
    private String sourceFile;
    private List<Line> code;
    private int fileCount;

    public void addLine(Line line) {
        code.add(line);
    }
}
