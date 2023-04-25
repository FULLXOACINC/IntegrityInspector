package io.integrityinspector.antlr.core;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import io.integrityinspector.antlr.model.CodeTree;
import io.integrityinspector.antlr.python.gen.Python3Lexer;
import io.integrityinspector.antlr.python.gen.Python3Parser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CodeTreeConverterImplTest {
    private static final String code =
            "def main():\n" +
                    "        print(\"Test\")\n" +
                    "        operation = input()";


    @Test
    public void convertCodeTreeNodePositiveTest() {
        CodeTreeConverter converter = new CodeTreeConverterImpl();
        CharStream charStream = CharStreams.fromString(code);
        Python3Lexer lexer = new Python3Lexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Python3Parser parser = new Python3Parser(tokens);
        ParseTree tree = parser.file_input();

        CodeTree ch3 = new CodeTree(689);
        CodeTree ch2 = new CodeTree(492);
        CodeTree ch1 = new CodeTree(260);
        ch2.getChildren().add(ch3);
        ch1.getChildren().add(ch2);
        CodeTree expected = new CodeTree(-1);
        expected.getChildren().add(ch1);
        ch3.setParent(ch2);
        ch2.setParent(ch1);
        ch1.setParent(expected);
        CodeTree actual = converter.convertCodeTreeNode(tree);
        assertEquals(expected, actual);
    }
}
