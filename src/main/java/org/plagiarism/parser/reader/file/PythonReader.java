package org.plagiarism.parser.reader.file;

import org.plagiarism.antlr.core.CodeTree;
import org.plagiarism.antlr.core.CodeTreeNodeConverter;
import org.plagiarism.antlr.core.CodeTreeUtil;
import org.plagiarism.antlr.python.PythonCodeTreeNodeConverter;
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
    private static final DefaultLineCleaner LINE_CLEANER = new DefaultLineCleaner();
    private static final String PYTHON_FILE_LINE_DELIMITER = "\n";
    private static final DefaultCodeFileReader FILER_READER = new DefaultCodeFileReader(PYTHON_FILE_LINE_DELIMITER);
    private static final LineForCheckExtractor LINE_FOR_CHECK_EXTRACTOR = new LineForCheckExtractor();
    private static final CodeTreeNodeConverter CODE_TREE_NODE_CONVERTER = new PythonCodeTreeNodeConverter();
    private static final String LANGUAGE = "Python";


    @Override
    public CodeFile read(String file) throws IOException {
        String fileContext = FILER_READER.readFileFullContext(file);
        return convertToCodeFile(file, fileContext);
    }

    protected CodeFile convertToCodeFile(String file, String fileContext) {
        String commentFilteredFileContext = FILE_COMMENT_CLEANER.removeComments(fileContext);
        List<Line> lineForCheck = LINE_FOR_CHECK_EXTRACTOR.extractLinesForCheck(
                commentFilteredFileContext,
                PYTHON_FILE_LINE_DELIMITER,
                this::isLineNeedAddToCheckList,
                x -> LINE_CLEANER.clearLine(PythonReader.LINE_COMMENT_CLEANER.removeComments(x))
        );
        CodeTree codeTree = CodeTreeUtil.parseCodeTree(file, commentFilteredFileContext, CODE_TREE_NODE_CONVERTER);
        return new CodeFile(file, lineForCheck, fileContext.length(), codeTree, LANGUAGE);
    }


    private boolean isLineNeedAddToCheckList(String line) {
        String filtered = LINE_CLEANER.clearLine(LINE_COMMENT_CLEANER.removeComments(line));
        if (filtered.isEmpty()) {
            return false;
        }
        return !filtered.startsWith("from") && !filtered.startsWith("import");
    }

}
