package org.plagiarism.parser.reader.file;

import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Line;
import org.plagiarism.parser.cleaner.file.CommonFileCommentCleaner;
import org.plagiarism.parser.cleaner.line.DefaultLineCleaner;

import java.io.IOException;
import java.util.List;

public class CppReader implements CodeReader {
    private static final CommonFileCommentCleaner FILE_COMMENT_CLEANER = new CommonFileCommentCleaner();
    private static final DefaultLineCleaner LINE_CLEANER = new DefaultLineCleaner();
    private static final String JAVA_FILE_LINE_DELIMITER = "\n";
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
                LINE_CLEANER::clearLine
        );
        return new CodeFile(file, lineForCheck, fileContext.length(), null, "C++");
    }


    private boolean isLineNeedAddToCheckList(String line) {
        String filtered = LINE_CLEANER.clearLine(line);
        if (filtered.isEmpty()) {
            return false;
        }
        return !filtered.startsWith("include") &&
                !filtered.startsWith("pragma") &&
                !filtered.startsWith("import");
    }

}
