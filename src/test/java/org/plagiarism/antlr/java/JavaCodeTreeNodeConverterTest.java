package org.plagiarism.antlr.java;

import org.junit.Test;
import org.plagiarism.antlr.core.CodeTree;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class JavaCodeTreeNodeConverterTest {
    private static final String code = "public class Test {\n" + "\n" + "\tpublic static void main(String[] args){\n" + "\n" + "\t\tSystem.out.println(\"Hello, World!\");\n" + "\t\n" + "\t}\n" + "}";

    @Test
    public void readPositiveTest() throws IOException {
        CodeTree actual = new JavaCodeTreeNodeConverter().convertToCodeTreeNode(code);
        System.out.println(actual);
        assertEquals("expected", actual);
    }
}
