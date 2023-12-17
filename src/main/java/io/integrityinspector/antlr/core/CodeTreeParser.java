package io.integrityinspector.antlr.core;

import io.integrityinspector.antlr.model.CodeTree;

public interface CodeTreeParser {
    CodeTree parseCodeTree(String fileName, String content, CodeTreeNodeConverter converter);
}
