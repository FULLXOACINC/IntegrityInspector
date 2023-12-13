package io.integrityinspector.parser.reader.file;

import io.integrityinspector.antlr.core.CodeTreeNodeConverter;
import io.integrityinspector.antlr.java.JavaCodeTreeNodeConverter;
import io.integrityinspector.antlr.model.CodeTree;
import io.integrityinspector.antlr.python.PythonCodeTreeNodeConverter;
import io.integrityinspector.config.AdditionalFileExtensionConfig;
import io.integrityinspector.config.AppConfig;
import io.integrityinspector.config.ParserConfig;
import io.integrityinspector.config.ProgrammingLangLineStartExclusion;
import io.integrityinspector.model.CodeFile;
import io.integrityinspector.model.CodeFileTree;
import io.integrityinspector.parser.cleaner.comment.CommentCleaner;
import io.integrityinspector.parser.cleaner.comment.CommonFileCommentCleaner;
import io.integrityinspector.parser.cleaner.comment.PythonFileCommentCleaner;
import io.integrityinspector.parser.cleaner.line.DefaultLineCleaner;
import io.integrityinspector.parser.cleaner.line.LineCleaner;
import io.integrityinspector.parser.cleaner.line.PythonLineCommentCleaner;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.integrityinspector.checker.TreeSimilarityCalculator.NOT_SUPPORT_CODE;

public class CodeReaderFactoryFactory {
    private static final String FILE_LINE_DELIMITER = "\n";
    public static final String JAVA_LANG = "Java";
    public static final String PYTHON_LANG = "Python";
    public static final String CPP_LANG = "C++";
    public static final String JS_LANG = "JS";
    public static final String C_SHARP_LANG = "C#";
    public static final String TEXT = "TEXT";
    public static final String PYTHON_KEY = "py";
    public static final String JAVA_KEY = "java";
    public static final String C_KEY = "c";
    public static final String JS_KEY = "js";
    public static final String C_SHARP_KEY = "cs";
    public static final String IPYNB_KEY = "ipynb";

    public CodeReaderFactory<? extends CodeFile> createCodeReaderFactory(AppConfig appConfig) {
        ParserConfig parseCodeConfig = appConfig.getParseCodeConfig();
        List<AdditionalFileExtensionConfig> additionalFileExtensions = parseCodeConfig.getAdditionalFileExtensions();
        ProgrammingLangLineStartExclusion langLineStartExclusion = parseCodeConfig.getProgrammingLangLineStartExclusion();


        CommentCleaner fileCommentCleanerDefault = new CommonFileCommentCleaner();
        LineCleaner defaultLineCleaner = new DefaultLineCleaner();
        DefaultCodeFileReader defaultCodeFileReader = new DefaultCodeFileReader(FILE_LINE_DELIMITER);
        LineForCheckExtractor lineForCheckExtractor = new LineForCheckExtractor(FILE_LINE_DELIMITER);

        LineValidator lineValidatorDefault = new LineValidatorImpl(Collections.emptySet(), defaultLineCleaner);
        LineValidator lineValidatorForCpp = new LineValidatorImpl(langLineStartExclusion.getCpp(), defaultLineCleaner);
        LineValidator lineValidatorCSharp = new LineValidatorImpl(langLineStartExclusion.getCSharp(), defaultLineCleaner);
        LineValidator lineValidatorJava = new LineValidatorImpl(langLineStartExclusion.getJava(), defaultLineCleaner);
        LineValidator lineValidatorJs = new LineValidatorImpl(langLineStartExclusion.getJs(), defaultLineCleaner);
        LineValidator lineValidatorPython = new LineValidatorImpl(langLineStartExclusion.getPython(), defaultLineCleaner);

        CommentCleaner pythonFileCommentCleaner = new PythonFileCommentCleaner();
        LineCleaner pythonLineCommentCleaner = new PythonLineCommentCleaner();
        if (appConfig.getParseCodeConfig().getNeedParseTree()) {
            return createCodeFileCodeReaderTreeFactory(additionalFileExtensions,
                    fileCommentCleanerDefault,
                    defaultLineCleaner,
                    defaultCodeFileReader,
                    lineForCheckExtractor,
                    lineValidatorDefault,
                    lineValidatorJava,
                    lineValidatorPython,
                    pythonFileCommentCleaner,
                    pythonLineCommentCleaner
            );
        }
        return createCodeFileCodeReaderFactory(additionalFileExtensions,
                fileCommentCleanerDefault,
                defaultLineCleaner,
                defaultCodeFileReader,
                lineForCheckExtractor,
                lineValidatorDefault,
                lineValidatorForCpp,
                lineValidatorCSharp,
                lineValidatorJava,
                lineValidatorJs,
                lineValidatorPython,
                pythonFileCommentCleaner,
                pythonLineCommentCleaner
        );
    }

    private CodeReaderFactory<CodeFile> createCodeFileCodeReaderFactory(List<AdditionalFileExtensionConfig> additionalFileExtensions,
                                                                        CommentCleaner fileCommentCleanerDefault,
                                                                        LineCleaner defaultLineCleaner,
                                                                        DefaultCodeFileReader defaultCodeFileReader,
                                                                        LineForCheckExtractor lineForCheckExtractor,
                                                                        LineValidator lineValidatorDefault,
                                                                        LineValidator lineValidatorForCpp,
                                                                        LineValidator lineValidatorCSharp,
                                                                        LineValidator lineValidatorJava,
                                                                        LineValidator lineValidatorJs,
                                                                        LineValidator lineValidatorPython,
                                                                        CommentCleaner pythonFileCommentCleaner,
                                                                        LineCleaner pythonLineCommentCleaner) {

        Map<String, CodeReader<CodeFile>> readerMap = new HashMap<>();
        CodeFileReader pythonReader = new CodeFileReader(PYTHON_LANG, pythonFileCommentCleaner, pythonLineCommentCleaner, defaultCodeFileReader, lineForCheckExtractor, lineValidatorPython);
        readerMap.put(PYTHON_KEY, pythonReader);
        readerMap.put(JAVA_KEY, new CodeFileReader(JAVA_LANG, fileCommentCleanerDefault, defaultLineCleaner, defaultCodeFileReader, lineForCheckExtractor, lineValidatorJava));
        readerMap.put(C_KEY, new CodeFileReader(CPP_LANG, fileCommentCleanerDefault, defaultLineCleaner, defaultCodeFileReader, lineForCheckExtractor, lineValidatorForCpp));
        readerMap.put(JS_KEY, new CodeFileReader(JS_LANG, fileCommentCleanerDefault, defaultLineCleaner, defaultCodeFileReader, lineForCheckExtractor, lineValidatorJs));
        readerMap.put(C_SHARP_KEY, new CodeFileReader(C_SHARP_LANG, fileCommentCleanerDefault, defaultLineCleaner, defaultCodeFileReader, lineForCheckExtractor, lineValidatorCSharp));
        readerMap.put(IPYNB_KEY, new IpynbReader<>(pythonReader));
        CodeReader<CodeFile> defaultReader = new CodeFileReader(TEXT, fileCommentCleanerDefault, defaultLineCleaner, defaultCodeFileReader, lineForCheckExtractor, lineValidatorDefault);

        return new CodeReaderFactory<>(defaultReader, readerMap, additionalFileExtensions);
    }

    private CodeReaderFactory<CodeFileTree> createCodeFileCodeReaderTreeFactory(List<AdditionalFileExtensionConfig> additionalFileExtensions,
                                                                                CommentCleaner fileCommentCleanerDefault,
                                                                                LineCleaner defaultLineCleaner,
                                                                                DefaultCodeFileReader defaultCodeFileReader,
                                                                                LineForCheckExtractor lineForCheckExtractor,
                                                                                LineValidator lineValidatorDefault,
                                                                                LineValidator lineValidatorJava,
                                                                                LineValidator lineValidatorPython,
                                                                                CommentCleaner pythonFileCommentCleaner,
                                                                                LineCleaner pythonLineCommentCleaner) {

        CodeTreeNodeConverter javaCodeTreeNodeConverter = new JavaCodeTreeNodeConverter();
        CodeTreeNodeConverter pythonCodeTreeNodeConverter = new PythonCodeTreeNodeConverter();
        CodeTreeNodeConverter defaultCodeTreeNodeConverter = content -> new CodeTree(NOT_SUPPORT_CODE);

        Map<String, CodeReader<CodeFileTree>> readerMap = new HashMap<>();
        CodeFileTreeReader pythonReader = new CodeFileTreeReader(PYTHON_LANG, pythonFileCommentCleaner, pythonLineCommentCleaner, defaultCodeFileReader, lineForCheckExtractor, lineValidatorPython, pythonCodeTreeNodeConverter);
        readerMap.put(PYTHON_KEY, pythonReader);
        readerMap.put(JAVA_KEY, new CodeFileTreeReader(JAVA_LANG, fileCommentCleanerDefault, defaultLineCleaner, defaultCodeFileReader, lineForCheckExtractor, lineValidatorJava, javaCodeTreeNodeConverter));
        readerMap.put(IPYNB_KEY, new IpynbReader<>(pythonReader));
        CodeReader<CodeFileTree> defaultReader = new CodeFileTreeReader(TEXT, fileCommentCleanerDefault, defaultLineCleaner, defaultCodeFileReader, lineForCheckExtractor, lineValidatorDefault, defaultCodeTreeNodeConverter);

        return new CodeReaderFactory<>(defaultReader, readerMap, additionalFileExtensions);
    }

}
