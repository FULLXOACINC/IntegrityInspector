package org.plagiarism.parser.reader.file;

import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Line;
import org.plagiarism.parser.cleaner.line.DefaultLineCleaner;

import java.io.IOException;
import java.util.List;

public class DefaultReader implements CodeReader {

    private static final DefaultLineCleaner LINE_CLEANER = new DefaultLineCleaner();
    private static final String PYTHON_FILE_LINE_DELIMITER = "\n";
    private static final DefaultCodeFileReader FILER_READER = new DefaultCodeFileReader(PYTHON_FILE_LINE_DELIMITER);
    private static final LineForCheckExtractor lineForCheckExtractor = new LineForCheckExtractor();


    @Override
    public CodeFile read(String file) throws IOException {
        String fileContext = FILER_READER.readFileFullContext(file);
        List<Line> lineForCheck = lineForCheckExtractor.extractLinesForCheck(
                fileContext,
                PYTHON_FILE_LINE_DELIMITER,
                x -> true,
                LINE_CLEANER::clearLine
        );
        return new CodeFile(file, lineForCheck, fileContext.length());
    }

}
