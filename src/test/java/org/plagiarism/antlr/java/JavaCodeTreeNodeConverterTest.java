package org.plagiarism.antlr.java;

import org.junit.Test;
import org.plagiarism.antlr.core.CodeTree;

import static org.junit.Assert.assertEquals;

public class JavaCodeTreeNodeConverterTest {
    private static final String code =
            "public class Test {\n" + "\n" +
                    "\tpublic static void test(String[] args){\n" +
                    "\n" + "\t\tSystem.out.println(\"Hello, World!\");\n" + "\t\n" +
                    "\t}\n" +
                    "}";

    @Test
    public void readPositiveTest() {
        CodeTree ch3 = new CodeTree(299);
        CodeTree ch2 = new CodeTree(293);
        CodeTree ch1 = new CodeTree(261);
        ch1.getChildren().add(ch2);
        ch1.getChildren().add(ch3);
        CodeTree expected = new CodeTree(-1);
        expected.getChildren().add(ch1);
        ch3.setParent(ch1);
        ch2.setParent(ch1);
        ch1.setParent(expected);
        CodeTree actual = new JavaCodeTreeNodeConverter().convertToCodeTreeNode(code);
        assertEquals(expected, actual);
    }
}
