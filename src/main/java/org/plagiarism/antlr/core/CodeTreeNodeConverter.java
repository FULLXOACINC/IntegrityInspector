package org.plagiarism.antlr.core;

import org.plagiarism.antlr.model.CodeTree;

public interface CodeTreeNodeConverter {
    CodeTree convertToCodeTreeNode(String content);
}
