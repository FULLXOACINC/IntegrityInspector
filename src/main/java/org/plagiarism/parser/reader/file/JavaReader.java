package org.plagiarism.parser.reader.file;

import org.plagiarism.antlr.core.CodeTree;
import org.plagiarism.antlr.core.CodeTreeNodeConverter;
import org.plagiarism.antlr.core.CodeTreeUtil;
import org.plagiarism.antlr.java.JavaCodeTreeNodeConverter;
import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Line;
import org.plagiarism.parser.cleaner.file.CommonFileCommentCleaner;
import org.plagiarism.parser.cleaner.line.DefaultLineCleaner;

import java.io.IOException;
import java.util.List;

public class JavaReader implements CodeReader {
    private static final CommonFileCommentCleaner FILE_COMMENT_CLEANER = new CommonFileCommentCleaner();
    private static final DefaultLineCleaner LINE_CLEANER = new DefaultLineCleaner();
    private static final String JAVA_FILE_LINE_DELIMITER = "\n";
    private static final DefaultCodeFileReader FILER_READER = new DefaultCodeFileReader(JAVA_FILE_LINE_DELIMITER);
    private static final LineForCheckExtractor LINE_FOR_CHECK_EXTRACTOR = new LineForCheckExtractor();
    private static final CodeTreeNodeConverter CODE_TREE_NODE_CONVERTER = new JavaCodeTreeNodeConverter();
    private static final String LANGUAGE = "Java";


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
        CodeTree codeTree = CodeTreeUtil.parseCodeTree(file, commentFilteredFileContext, CODE_TREE_NODE_CONVERTER);
        return new CodeFile(file, lineForCheck, fileContext.length(), codeTree, LANGUAGE);
    }


    private boolean isLineNeedAddToCheckList(String line) {
        String filtered = LINE_CLEANER.clearLine(line);
        if (filtered.isEmpty()) {
            return false;
        }
        return !filtered.startsWith("package") && !filtered.startsWith("import");
    }

}
