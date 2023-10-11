package io.integrityinspector.parser.cleaner.comment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFileCommentCleaner implements CommentCleaner {
    private static final Pattern LINE_COMMENT_PATTERN_SINGLE_PATTERN = Pattern.compile("/\\*([\\S\\s]+?)\\*/");
    private static final Pattern LINE_COMMENT_PATTERN_MULTIPLE_PATTERN = Pattern.compile("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)");

    @Override
    public String removeComments(String code) {
        Matcher matcherSingleLine = LINE_COMMENT_PATTERN_SINGLE_PATTERN.matcher(code);
        return LINE_COMMENT_PATTERN_MULTIPLE_PATTERN
                .matcher(
                        matcherSingleLine.replaceAll("")
                )
                .replaceAll("");
    }
}
