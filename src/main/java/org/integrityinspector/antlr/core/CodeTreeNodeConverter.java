package org.integrityinspector.antlr.core;

import org.integrityinspector.antlr.model.CodeTree;

public interface CodeTreeNodeConverter {
    CodeTree convertToCodeTreeNode(String content);
}
