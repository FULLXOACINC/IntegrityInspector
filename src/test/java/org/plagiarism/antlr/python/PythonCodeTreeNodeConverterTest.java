package org.plagiarism.antlr.python;

import org.junit.Test;
import org.plagiarism.antlr.core.CodeTree;

import static org.junit.Assert.assertEquals;

public class PythonCodeTreeNodeConverterTest {
    private static final String code =
            "def main():\n" +
                    "        print(\"Test\")\n" +
                    "        operation = input()";

    @Test
    public void readPositiveTest() {
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
        CodeTree actual = new PythonCodeTreeNodeConverter().convertToCodeTreeNode(code);
        assertEquals(expected, actual);
    }
}
