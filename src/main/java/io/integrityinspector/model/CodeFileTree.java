package io.integrityinspector.model;

import io.integrityinspector.antlr.model.CodeTree;
import lombok.Getter;

import java.util.List;

@Getter
public class CodeFileTree extends CodeFile {
    private final CodeTree codeTree;

    public CodeFileTree(String sourceFile, List<Line> code, int fileLineCount, CodeTree codeTree, String language) {
        super(sourceFile, code, fileLineCount, language);
        this.codeTree = codeTree;
    }
}
