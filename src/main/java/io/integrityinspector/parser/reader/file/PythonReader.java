package io.integrityinspector.parser.reader.file;

import io.integrityinspector.antlr.core.CodeTreeNodeConverter;
import io.integrityinspector.antlr.core.CodeTreeUtil;
import io.integrityinspector.antlr.model.CodeTree;
import io.integrityinspector.antlr.python.PythonCodeTreeNodeConverter;
import io.integrityinspector.model.CodeFile;
import io.integrityinspector.model.Line;
import io.integrityinspector.parser.cleaner.comment.CommentCleaner;
import io.integrityinspector.parser.cleaner.comment.PythonFileCommentCleaner;
import io.integrityinspector.parser.cleaner.comment.PythonLineCommentCleaner;
import io.integrityinspector.parser.cleaner.line.DefaultLineCleaner;
import io.integrityinspector.parser.cleaner.line.LineCleaner;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.List;

@EqualsAndHashCode
public class PythonReader implements CodeReader {
    private static final CommentCleaner FILE_COMMENT_CLEANER = new PythonFileCommentCleaner();
    private static final CommentCleaner LINE_COMMENT_CLEANER = new PythonLineCommentCleaner();
    private static final LineCleaner LINE_CLEANER = new DefaultLineCleaner();
    private static final String PYTHON_FILE_LINE_DELIMITER = "\n";
    private static final DefaultCodeFileReader FILER_READER = new DefaultCodeFileReader(PYTHON_FILE_LINE_DELIMITER);
    private static final LineForCheckExtractor LINE_FOR_CHECK_EXTRACTOR = new LineForCheckExtractor();
    private static final CodeTreeNodeConverter CODE_TREE_NODE_CONVERTER = new PythonCodeTreeNodeConverter();
    private static final String LANGUAGE = "Python";

    private final Boolean needParseTree;

    public PythonReader(Boolean needParseTree) {
        this.needParseTree = needParseTree;
    }


    @Override
    public CodeFile read(String file) throws IOException {
        String fileContext = FILER_READER.readFileFullContext(file);
        return convertToCodeFile(file, fileContext);
    }

    public CodeFile convertToCodeFile(String file, String fileContext) {
        String commentFilteredFileContext = FILE_COMMENT_CLEANER.removeComments(fileContext);
        List<Line> lineForCheck = LINE_FOR_CHECK_EXTRACTOR.extractLinesForCheck(
                commentFilteredFileContext,
                PYTHON_FILE_LINE_DELIMITER,
                this::isLineNeedAddToCheckList,
                x -> LINE_CLEANER.cleanLine(PythonReader.LINE_COMMENT_CLEANER.removeComments(x))
        );

        CodeTree codeTree = new CodeTree(-1);
        if (needParseTree) {
            codeTree = CodeTreeUtil.parseCodeTree(file, commentFilteredFileContext, CODE_TREE_NODE_CONVERTER);
        }

        return new CodeFile(file, lineForCheck, fileContext.length(), codeTree, LANGUAGE);
    }


    public boolean isLineNeedAddToCheckList(String line) {
        String filtered = LINE_CLEANER.cleanLine(LINE_COMMENT_CLEANER.removeComments(line));
        if (filtered.isEmpty()) {
            return false;
        }
        return !filtered.startsWith("from") && !filtered.startsWith("import");
    }

}
