package io.integrityinspector.model;

import io.integrityinspector.antlr.model.CodeTree;
import lombok.AllArgsConstructor;
import lombok.Data;

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
