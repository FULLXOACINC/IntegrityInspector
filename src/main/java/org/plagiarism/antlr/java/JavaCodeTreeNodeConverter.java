package org.plagiarism.antlr.java;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.plagiarism.antlr.core.CodeTree;
import org.plagiarism.antlr.core.CodeTreeConverter;
import org.plagiarism.antlr.core.CodeTreeNodeConverter;
import org.plagiarism.antlr.java.gen.JavaLexer;
import org.plagiarism.antlr.java.gen.JavaParser;
import org.plagiarism.antlr.java.gen.JavaParserBaseListener;

public class JavaCodeTreeNodeConverter implements CodeTreeNodeConverter {
    private static final CodeTreeConverter CONVERTER = new CodeTreeConverter();

    @Override
    public CodeTree convertToCodeTreeNode(String content) {
        CharStream charStream = CharStreams.fromString(content);
        JavaLexer lexer = new JavaLexer(charStream);
        JavaParser parser = new JavaParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        JavaParserBaseListener listener = new JavaParserBaseListener();
        walker.walk(listener, tree);
        return CONVERTER.convertCodeTreeNode(tree);
    }
}
