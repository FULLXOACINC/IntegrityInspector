package org.plagiarism.antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class CodeTreeConverter {

    public CodeTree convertCodeTreeNode(ParseTree tree) {
        if (tree instanceof ParserRuleContext) {
            ParserRuleContext ctx = (ParserRuleContext) tree;
            CodeTree node = new CodeTree(ctx.getText());
            for (int i = 0; i < ctx.getChildCount(); i++) {
                CodeTree child = convertCodeTreeNode(ctx.getChild(i));
                if (child == null) {
                    return node;
                }
                node.getChildren().add(child);
                child.setParent(node);
            }
            return node;
        } else {
            return null;
        }
    }
}
