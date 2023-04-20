package org.integrityinspector.antlr.core;

import org.antlr.v4.runtime.tree.ParseTree;
import org.integrityinspector.antlr.model.CodeTree;

public interface CodeTreeConverter {
    CodeTree convertCodeTreeNode(ParseTree tree);
}
