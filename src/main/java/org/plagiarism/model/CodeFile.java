package org.plagiarism.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.plagiarism.antlr.model.CodeTree;

import java.util.List;

@Data
@AllArgsConstructor
public class CodeFile {
    private String sourceFile;
    private List<Line> code;
    private int fileLineCount;
    private CodeTree codeTree;
    private String language;
}
