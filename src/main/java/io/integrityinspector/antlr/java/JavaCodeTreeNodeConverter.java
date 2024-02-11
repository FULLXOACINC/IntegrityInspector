package io.integrityinspector.antlr.java;

import io.integrityinspector.antlr.core.CodeTreeConverter;
import io.integrityinspector.antlr.core.CodeTreeNodeConverter;
import io.integrityinspector.antlr.core.DefaultCodeTreeConverter;
import io.integrityinspector.antlr.java.gen.JavaLexer;
import io.integrityinspector.antlr.java.gen.JavaParser;
import io.integrityinspector.antlr.java.gen.JavaParserBaseListener;
import io.integrityinspector.antlr.model.CodeTree;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class JavaCodeTreeNodeConverter implements CodeTreeNodeConverter {
    private final CodeTreeConverter codeTreeConverter;

    public JavaCodeTreeNodeConverter() {
        codeTreeConverter = new DefaultCodeTreeConverter();
    }

    JavaCodeTreeNodeConverter(CodeTreeConverter codeTreeConverter) {
        this.codeTreeConverter = codeTreeConverter;
    }

    @Override
    public CodeTree convertToCodeTreeNode(String content) {
        CharStream charStream = CharStreams.fromString(content);
        JavaLexer lexer = new JavaLexer(charStream);
        JavaParser parser = new JavaParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        JavaParserBaseListener listener = new JavaParserBaseListener();
        walker.walk(listener, tree);
        return codeTreeConverter.convertCodeTreeNode(tree);
    }
}
