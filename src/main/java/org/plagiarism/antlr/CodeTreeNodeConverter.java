package org.plagiarism.antlr;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class CodeTreeNodeConverter {

    public CodeTreeNode convertCodeTreeNode(ParseTree tree) {
        if (tree instanceof ParserRuleContext) {
            ParserRuleContext ctx = (ParserRuleContext) tree;
            CodeTreeNode node = new CodeTreeNode(ctx.getText());
            for (int i = 0; i < ctx.getChildCount(); i++) {
                CodeTreeNode child = convertCodeTreeNode(ctx.getChild(i));
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
