package io.integrityinspector.parser.reader.file;

import io.integrityinspector.model.CodeFile;
import io.integrityinspector.model.Line;
import io.integrityinspector.parser.cleaner.comment.CommentCleaner;
import io.integrityinspector.parser.cleaner.line.LineCleaner;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.List;

@EqualsAndHashCode
public class CodeFileReader implements CodeReader<CodeFile> {
    private final String language;
    private final CommentCleaner commentCleaner;
    private final LineCleaner lineCleaner;
    private final DefaultCodeFileReader fileReader;
    private final LineForCheckExtractor lineForCheckExtractor;

    private final LineValidator lineValidator;

    public CodeFileReader(String language,
                          CommentCleaner commentCleaner,
                          LineCleaner lineCleaner,
                          DefaultCodeFileReader fileReader,
                          LineForCheckExtractor lineForCheckExtractor,
                          LineValidator lineValidator
    ) {
        this.language = language;
        this.commentCleaner = commentCleaner;
        this.lineCleaner = lineCleaner;
        this.fileReader = fileReader;
        this.lineForCheckExtractor = lineForCheckExtractor;
        this.lineValidator = lineValidator;
    }

    @Override
    public CodeFile read(String file) throws IOException {
        String fileContext = fileReader.readFileFullContext(file);
        return createCodeFile(file, fileContext);
    }

    @Override
    public CodeFile createCodeFile(String file, String fileContext) {
        String commentFilteredFileContext = commentCleaner.removeComments(fileContext);
        List<Line> lineForCheck = lineForCheckExtractor.extractLinesForCheck(
                commentFilteredFileContext,
                lineValidator::isLineNeedAddToCheckList,
                lineCleaner::cleanLine
        );
        return new CodeFile(file, lineForCheck, fileContext.length(), language);
    }


}
