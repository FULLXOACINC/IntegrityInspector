package org.plagiarism.antlr.python;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.plagiarism.antlr.core.CodeTreeConverter;
import org.plagiarism.antlr.core.CodeTreeConverterImpl;
import org.plagiarism.antlr.core.CodeTreeNodeConverter;
import org.plagiarism.antlr.model.CodeTree;
import org.plagiarism.antlr.python.gen.Python3Lexer;
import org.plagiarism.antlr.python.gen.Python3Parser;

public class PythonCodeTreeNodeConverter implements CodeTreeNodeConverter {
    private final CodeTreeConverter codeTreeConverter;

    public PythonCodeTreeNodeConverter() {
        codeTreeConverter = new CodeTreeConverterImpl();
    }

    PythonCodeTreeNodeConverter(CodeTreeConverter codeTreeConverter) {
        this.codeTreeConverter = codeTreeConverter;
    }

    @Override
    public CodeTree convertToCodeTreeNode(String content) {
        CharStream charStream = CharStreams.fromString(content);
        Python3Lexer lexer = new Python3Lexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Python3Parser parser = new Python3Parser(tokens);
        ParseTree tree = parser.file_input();
        return codeTreeConverter.convertCodeTreeNode(tree);
    }
}
