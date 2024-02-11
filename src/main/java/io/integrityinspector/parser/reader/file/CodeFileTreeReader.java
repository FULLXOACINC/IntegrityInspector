package io.integrityinspector.parser.reader.file;

import io.integrityinspector.antlr.core.CodeTreeNodeConverter;
import io.integrityinspector.antlr.core.CodeTreeParser;
import io.integrityinspector.antlr.model.CodeTree;
import io.integrityinspector.model.CodeFileTree;
import io.integrityinspector.model.Line;
import io.integrityinspector.parser.cleaner.comment.CommentCleaner;
import io.integrityinspector.parser.cleaner.line.LineCleaner;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.util.List;

@EqualsAndHashCode
@AllArgsConstructor
public class CodeFileTreeReader implements CodeReader<CodeFileTree> {
    private final String language;
    private final CommentCleaner commentCleaner;
    private final LineCleaner lineCleaner;
    private final CodeFileContextReader fileReader;
    private final LineForCheckExtractor defaultLineForCheckExtractor;
    private final LineValidator lineValidator;
    private final CodeTreeNodeConverter codeTreeNodeConverter;
    private final CodeTreeParser codeTreeParser;

    @Override
    public CodeFileTree read(String file) throws IOException {
        String fileContext = fileReader.readFileFullContext(file);
        return createCodeFile(file, fileContext);
    }

    @Override
    public CodeFileTree createCodeFile(String file, String fileContext) {
        String commentFilteredFileContent = commentCleaner.removeComments(fileContext);
        List<Line> lineForCheck = defaultLineForCheckExtractor.extractLinesForCheck(
                commentFilteredFileContent,
                lineValidator::isLineNeedAddToCheckList,
                lineCleaner::cleanLine
        );
        CodeTree codeTree = codeTreeParser.parseCodeTree(file, commentFilteredFileContent, codeTreeNodeConverter);
        return new CodeFileTree(file, lineForCheck, fileContext.length(), codeTree, language);
    }


}
