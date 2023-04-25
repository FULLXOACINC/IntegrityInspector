package io.integrityinspector.parser.reader.file;

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
public class CppReader implements CodeReader {
    private static final String LANGUAGE = "C++";
    private static final String JAVA_FILE_LINE_DELIMITER = "\n";
    private static final CommentCleaner FILE_COMMENT_CLEANER = new CommonFileCommentCleaner();
    private static final LineCleaner LINE_CLEANER = new DefaultLineCleaner();
    private static final DefaultCodeFileReader FILER_READER = new DefaultCodeFileReader(JAVA_FILE_LINE_DELIMITER);
    private static final LineForCheckExtractor LINE_FOR_CHECK_EXTRACTOR = new LineForCheckExtractor();


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
        return new CodeFile(file, lineForCheck, fileContext.length(), new CodeTree(-1), LANGUAGE);
    }


    private boolean isLineNeedAddToCheckList(String line) {
        String filtered = LINE_CLEANER.cleanLine(line);
        if (filtered.isEmpty()) {
            return false;
        }
        return !filtered.startsWith("include") &&
                !filtered.startsWith("pragma") &&
                !filtered.startsWith("import");
    }

}
