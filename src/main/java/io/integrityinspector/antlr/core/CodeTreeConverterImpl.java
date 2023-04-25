package io.integrityinspector.antlr.core;

import io.integrityinspector.antlr.model.CodeTree;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

public class CodeTreeConverterImpl implements CodeTreeConverter {
    @Override
    public CodeTree convertCodeTreeNode(ParseTree tree) {
        if (tree instanceof ParserRuleContext) {
            ParserRuleContext ctx = (ParserRuleContext) tree;
            CodeTree node = new CodeTree(ctx.invokingState);
            for (int index = 0; index < ctx.getChildCount(); index++) {
                CodeTree child = convertCodeTreeNode(ctx.getChild(index));
                if (child == null) {
                    return node;
                }
                node.getChildren().add(child);
                child.setParent(node);
            }
            return node;
        }
        //TODO without comments, the analysis is too deep and it is impossible to wait for the result
//        if (tree instanceof TerminalNode) {
//            TerminalNode terminalNode = (TerminalNode) tree;
//            return new CodeTree(terminalNode.getText());
//        }
        return null;
    }
}
