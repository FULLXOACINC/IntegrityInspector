package org.plagiarism.antlr.python;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.plagiarism.antlr.CodeTreeNode;
import org.plagiarism.antlr.CodeTreeNodeConverter;
import org.plagiarism.antlr.python.gen.Python3Lexer;
import org.plagiarism.antlr.python.gen.Python3Parser;

import java.io.IOException;

public class PythonCodeTreeNodeReader {
    private static final CodeTreeNodeConverter CONVERTER = new CodeTreeNodeConverter();


    public CodeTreeNode read(String path) throws IOException {
        CharStream charStream = CharStreams.fromFileName(path);
        Python3Lexer lexer = new Python3Lexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Python3Parser parser = new Python3Parser(tokens);
        ParseTree tree = parser.file_input();
        return CONVERTER.convertCodeTreeNode(tree);
    }
}
