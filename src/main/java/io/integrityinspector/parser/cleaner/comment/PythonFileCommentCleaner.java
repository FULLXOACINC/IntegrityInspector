package io.integrityinspector.parser.cleaner.comment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PythonFileCommentCleaner implements CommentCleaner {
    private static final Pattern SINGLE_LINE_COMMENT_PATTERN = Pattern.compile("(?m)^\\s*#.*$");
    private static final Pattern MULTI_LINE_COMMENT_PATTERN = Pattern.compile("(['\"])\\1\\1[\\d\\D]*?\\1{3}");

    @Override
    public String removeComments(String pythonCode) {

        Matcher matcherSingleLine = SINGLE_LINE_COMMENT_PATTERN.matcher(pythonCode);
        return MULTI_LINE_COMMENT_PATTERN
                .matcher(
                        matcherSingleLine.replaceAll("")
                )
                .replaceAll("");
    }
}
