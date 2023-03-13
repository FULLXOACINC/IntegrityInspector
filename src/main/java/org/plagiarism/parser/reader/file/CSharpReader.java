package org.plagiarism.parser.reader.file;

import lombok.EqualsAndHashCode;
import org.plagiarism.antlr.core.CodeTree;
import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Line;
import org.plagiarism.parser.cleaner.comment.CommentCleaner;
import org.plagiarism.parser.cleaner.comment.CommonFileCommentCleaner;
import org.plagiarism.parser.cleaner.line.DefaultLineCleaner;
import org.plagiarism.parser.cleaner.line.LineCleaner;

import java.io.IOException;
import java.util.List;

@EqualsAndHashCode
public class CSharpReader implements CodeReader {
    private static final CommentCleaner FILE_COMMENT_CLEANER = new CommonFileCommentCleaner();
    private static final LineCleaner LINE_CLEANER = new DefaultLineCleaner();
    private static final String C_SHARP_FILE_LINE_DELIMITER = "\n";
    private static final DefaultCodeFileReader FILER_READER = new DefaultCodeFileReader(C_SHARP_FILE_LINE_DELIMITER);
    private static final LineForCheckExtractor LINE_FOR_CHECK_EXTRACTOR = new LineForCheckExtractor();
    private static final String LANGUAGE = "C#";


    @Override
    public CodeFile read(String file) throws IOException {
        String fileContext = FILER_READER.readFileFullContext(file);
        String commentFilteredFileContext = FILE_COMMENT_CLEANER.removeComments(fileContext);
        List<Line> lineForCheck = LINE_FOR_CHECK_EXTRACTOR.extractLinesForCheck(
                commentFilteredFileContext,
                C_SHARP_FILE_LINE_DELIMITER,
                this::isLineNeedAddToCheckList,
                LINE_CLEANER::cleanLine
        );
        return new CodeFile(file, lineForCheck, fileContext.length(), new CodeTree("DEFAULT"), LANGUAGE);
    }


    public boolean isLineNeedAddToCheckList(String line) {
        String filtered = LINE_CLEANER.cleanLine(line);
        if (filtered.isEmpty()) {
            return false;
        }
        return !filtered.startsWith("using") && !filtered.startsWith("namespace");
    }

}
