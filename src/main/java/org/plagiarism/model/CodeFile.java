package org.plagiarism.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CodeFile {
    private String sourceFile;
    private List<Line> code;
    private int fileLineCount;

    public void addLine(Line line) {
        code.add(line);
    }
}
