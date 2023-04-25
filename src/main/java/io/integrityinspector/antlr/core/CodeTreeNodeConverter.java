package io.integrityinspector.antlr.core;

import io.integrityinspector.antlr.model.CodeTree;

public interface CodeTreeNodeConverter {
    CodeTree convertToCodeTreeNode(String content);
}
