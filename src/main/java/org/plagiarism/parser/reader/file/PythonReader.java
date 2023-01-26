package org.plagiarism.parser.reader.file;

import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Line;
import org.plagiarism.parser.cleaner.file.PythonFileCommentCleaner;
import org.plagiarism.parser.cleaner.line.DefaultLineCleaner;
import org.plagiarism.parser.cleaner.line.PythonLineCommentCleaner;

import java.io.IOException;
import java.util.List;

public class PythonReader implements CodeReader {
    private static final PythonFileCommentCleaner FILE_COMMENT_CLEANER = new PythonFileCommentCleaner();
    private static final PythonLineCommentCleaner LINE_COMMENT_CLEANER = new PythonLineCommentCleaner();
    //This constant RegEx use for remove all characters in the input string except for numbers, Latin letters,
    private static final DefaultLineCleaner LINE_CLEANER = new DefaultLineCleaner();
    private static final String PYTHON_FILE_LINE_DELIMITER = "\n";
    private static final DefaultCodeFileReader FILER_READER = new DefaultCodeFileReader(PYTHON_FILE_LINE_DELIMITER);
    private static final LineForCheckExtractor lineForCheckExtractor = new LineForCheckExtractor();


    @Override
    public CodeFile read(String pythonFile) throws IOException {
        String fileContext = FILER_READER.readFileFullContext(pythonFile);
        String commentFilteredFileContext = FILE_COMMENT_CLEANER.removeComments(fileContext);
        List<Line> lineForCheck = lineForCheckExtractor.extractLinesForCheck(
                commentFilteredFileContext,
                PYTHON_FILE_LINE_DELIMITER,
                this::isLineNeedAddToCheckList,
                x -> LINE_CLEANER.clearLine(PythonReader.LINE_COMMENT_CLEANER.removeComments(x))
        );
        return new CodeFile(pythonFile, lineForCheck, fileContext.length());
    }


    private boolean isLineNeedAddToCheckList(String line) {
        String filtered = LINE_CLEANER.clearLine(LINE_COMMENT_CLEANER.removeComments(line));
        if (filtered.isEmpty()) {
            return false;
        }
        return !filtered.startsWith("from") && !filtered.startsWith("import");
    }

}
