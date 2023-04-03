package org.plagiarism.antlr.java;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.Trees;
import org.junit.Test;
import org.plagiarism.antlr.model.CodeTree;
import org.plagiarism.antlr.core.CodeTreeConverter;
import org.plagiarism.antlr.java.gen.JavaLexer;
import org.plagiarism.antlr.java.gen.JavaParser;
import org.plagiarism.antlr.java.gen.JavaParserBaseListener;

import static org.junit.Assert.assertEquals;

public class JavaCodeTreeNodeConverterTest {
    private static final String code =
            "public class Test {\n" + "\n" +
                    "\tpublic static void test(String[] args){\n" +
                    "\n" + "\t\tSystem.out.println(\"Hello, World!\");\n" + "\t\n" +
                    "\t}\n" +
                    "}";

    @Test
    public void convertToCodeTreeNodePositiveTest() {
        CharStream charStream = CharStreams.fromString(code);
        JavaLexer lexer = new JavaLexer(charStream);
        JavaParser parser = new JavaParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.compilationUnit();
        ParseTreeWalker walker = new ParseTreeWalker();
        JavaParserBaseListener listener = new JavaParserBaseListener();
        walker.walk(listener, tree);

        CodeTreeConverter codeTreeConverter = param -> {
            String tree1String = Trees.toStringTree(param);
            String tree2String = Trees.toStringTree(tree);
            if(tree2String.equals(tree1String)){
                return new CodeTree(42);
            }
            return null;
        };
        CodeTree actual = new JavaCodeTreeNodeConverter(codeTreeConverter).convertToCodeTreeNode(code);
        assertEquals(new CodeTree(42), actual);
    }
}
