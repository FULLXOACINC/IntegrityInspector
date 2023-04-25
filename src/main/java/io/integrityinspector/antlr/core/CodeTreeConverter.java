package io.integrityinspector.antlr.core;

import io.integrityinspector.antlr.model.CodeTree;
import org.antlr.v4.runtime.tree.ParseTree;

public interface CodeTreeConverter {
    CodeTree convertCodeTreeNode(ParseTree tree);
}
