package io.integrityinspector.parser.reader.file;

import io.integrityinspector.antlr.core.CodeTreeNodeConverter;
import io.integrityinspector.antlr.core.CodeTreeUtil;
import io.integrityinspector.antlr.model.CodeTree;
import io.integrityinspector.model.CodeFileTree;
import io.integrityinspector.model.Line;
import io.integrityinspector.parser.cleaner.comment.CommentCleaner;
import io.integrityinspector.parser.cleaner.line.LineCleaner;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.List;

@EqualsAndHashCode
public class CodeFileTreeReader implements CodeReader<CodeFileTree> {
    private final String language;
    private final CommentCleaner commentCleaner;
    private final LineCleaner lineCleaner;
    private final DefaultCodeFileReader fileReader;
    private final LineForCheckExtractor lineForCheckExtractor;

    private final LineValidator lineValidator;
    private final CodeTreeNodeConverter codeTreeNodeConverter;

    public CodeFileTreeReader(String language,
                              CommentCleaner commentCleaner,
                              LineCleaner lineCleaner,
                              DefaultCodeFileReader fileReader,
                              LineForCheckExtractor lineForCheckExtractor,
                              LineValidator lineValidator,
                              CodeTreeNodeConverter codeTreeNodeConverter
    ) {
        this.language = language;
        this.commentCleaner = commentCleaner;
        this.lineCleaner = lineCleaner;
        this.fileReader = fileReader;
        this.lineForCheckExtractor = lineForCheckExtractor;
        this.lineValidator = lineValidator;
        this.codeTreeNodeConverter = codeTreeNodeConverter;
    }

    @Override
    public CodeFileTree read(String file) throws IOException {
        String fileContext = fileReader.readFileFullContext(file);
        return createCodeFile(file, fileContext);
    }

    @Override
    public CodeFileTree createCodeFile(String file, String fileContext) {
        String commentFilteredFileContext = commentCleaner.removeComments(fileContext);
        List<Line> lineForCheck = lineForCheckExtractor.extractLinesForCheck(
                commentFilteredFileContext,
                lineValidator::isLineNeedAddToCheckList,
                lineCleaner::cleanLine
        );
        CodeTree codeTree = CodeTreeUtil.parseCodeTree(file, commentFilteredFileContext, codeTreeNodeConverter);
        return new CodeFileTree(file, lineForCheck, fileContext.length(), codeTree, language);
    }


}
