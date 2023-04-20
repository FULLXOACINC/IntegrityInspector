package org.plagiarism.antlr.core;

import org.antlr.v4.runtime.tree.ParseTree;
import org.plagiarism.antlr.model.CodeTree;

public interface CodeTreeConverter {
    CodeTree convertCodeTreeNode(ParseTree tree);
}
