package org.plagiarism.antlr.core;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;
import org.plagiarism.antlr.java.gen.JavaLexer;
import org.plagiarism.antlr.java.gen.JavaParser;
import org.plagiarism.antlr.java.gen.JavaParserBaseListener;

public class CodeTreeConverterTest {
    private static final String code = "public class Test {\n" + "\n" + "\tpublic static void main(String[] args){\n" + "\n" + "\t\tSystem.out.println(\"Hello, World!\");\n" + "\t\n" + "\t}\n" + "}";


    @Test
    public void t() {
        CodeTreeConverter converter = new CodeTreeConverter();
        CharStream charStream = CharStreams.fromString(code);
        JavaLexer lexer = new JavaLexer(charStream);
        JavaParser parser = new JavaParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        JavaParserBaseListener listener = new JavaParserBaseListener();
        walker.walk(listener, tree);

        converter.convertCodeTreeNode(tree);
    }
}
