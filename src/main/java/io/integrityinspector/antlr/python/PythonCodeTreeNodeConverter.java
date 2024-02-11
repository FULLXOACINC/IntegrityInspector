package io.integrityinspector.antlr.python;

import io.integrityinspector.antlr.core.CodeTreeConverter;
import io.integrityinspector.antlr.core.CodeTreeNodeConverter;
import io.integrityinspector.antlr.core.DefaultCodeTreeConverter;
import io.integrityinspector.antlr.model.CodeTree;
import io.integrityinspector.antlr.python.gen.Python3Lexer;
import io.integrityinspector.antlr.python.gen.Python3Parser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class PythonCodeTreeNodeConverter implements CodeTreeNodeConverter {
    private final CodeTreeConverter codeTreeConverter;

    public PythonCodeTreeNodeConverter() {
        codeTreeConverter = new DefaultCodeTreeConverter();
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
