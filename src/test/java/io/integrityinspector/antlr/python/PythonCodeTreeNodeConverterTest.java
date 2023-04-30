package io.integrityinspector.antlr.python;

import io.integrityinspector.antlr.core.CodeTreeConverter;
import io.integrityinspector.antlr.model.CodeTree;
import io.integrityinspector.antlr.python.gen.Python3Lexer;
import io.integrityinspector.antlr.python.gen.Python3Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.Trees;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PythonCodeTreeNodeConverterTest {
    private static final String code =
            "def main():\n" +
                    "        print(\"Test\")\n" +
                    "        operation = input()";

    @Test
    public void convertToCodeTreeNodePositiveTest() {
        CharStream charStream = CharStreams.fromString(code);
        Python3Lexer lexer = new Python3Lexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Python3Parser parser = new Python3Parser(tokens);
        ParseTree tree = parser.file_input();

        CodeTreeConverter codeTreeConverter = param -> {
            String tree1String = Trees.toStringTree(param);
            String tree2String = Trees.toStringTree(tree);
            if (tree2String.equals(tree1String)) {
                return new CodeTree(42);
            }
            return null;
        };
        CodeTree actual = new PythonCodeTreeNodeConverter(codeTreeConverter).convertToCodeTreeNode(code);
        assertEquals(new CodeTree(42), actual);
    }
}
