package io.integrityinspector.parser.reader.file;

import io.integrityinspector.antlr.core.CodeTreeNodeConverter;
import io.integrityinspector.antlr.core.CodeTreeUtil;
import io.integrityinspector.antlr.java.JavaCodeTreeNodeConverter;
import io.integrityinspector.antlr.model.CodeTree;
import io.integrityinspector.model.CodeFile;
import io.integrityinspector.model.Line;
import io.integrityinspector.parser.cleaner.comment.CommentCleaner;
import io.integrityinspector.parser.cleaner.comment.CommonFileCommentCleaner;
import io.integrityinspector.parser.cleaner.line.DefaultLineCleaner;
import io.integrityinspector.parser.cleaner.line.LineCleaner;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.List;

@EqualsAndHashCode
public class JavaReader implements CodeReader {
    private static final CommentCleaner FILE_COMMENT_CLEANER = new CommonFileCommentCleaner();
    private static final LineCleaner LINE_CLEANER = new DefaultLineCleaner();
    private static final String JAVA_FILE_LINE_DELIMITER = "\n";
    private static final DefaultCodeFileReader FILER_READER = new DefaultCodeFileReader(JAVA_FILE_LINE_DELIMITER);
    private static final LineForCheckExtractor LINE_FOR_CHECK_EXTRACTOR = new LineForCheckExtractor();
    private static final CodeTreeNodeConverter CODE_TREE_NODE_CONVERTER = new JavaCodeTreeNodeConverter();
    private static final String LANGUAGE = "Java";

    private final Boolean needParseTree;

    public JavaReader(Boolean needParseTree) {
        this.needParseTree = needParseTree;
    }

    @Override
    public CodeFile read(String file) throws IOException {
        String fileContext = FILER_READER.readFileFullContext(file);
        String commentFilteredFileContext = FILE_COMMENT_CLEANER.removeComments(fileContext);
        List<Line> lineForCheck = LINE_FOR_CHECK_EXTRACTOR.extractLinesForCheck(
                commentFilteredFileContext,
                JAVA_FILE_LINE_DELIMITER,
                this::isLineNeedAddToCheckList,
                LINE_CLEANER::cleanLine
        );
        CodeTree codeTree = new CodeTree(-1);
        if (needParseTree) {
            codeTree = CodeTreeUtil.parseCodeTree(file, commentFilteredFileContext, CODE_TREE_NODE_CONVERTER);
        }

        return new CodeFile(file, lineForCheck, fileContext.length(), codeTree, LANGUAGE);
    }


    public boolean isLineNeedAddToCheckList(String line) {
        String filtered = LINE_CLEANER.cleanLine(line);
        if (filtered.isEmpty()) {
            return false;
        }
        return !filtered.startsWith("package") && !filtered.startsWith("import");
    }

}
