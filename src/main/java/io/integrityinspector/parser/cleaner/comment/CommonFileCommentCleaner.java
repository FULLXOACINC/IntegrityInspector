package io.integrityinspector.parser.cleaner.comment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonFileCommentCleaner implements CommentCleaner {
    private static final Logger LOG = LoggerFactory.getLogger(CommonFileCommentCleaner.class);
    private static final Pattern LINE_COMMENT_PATTERN_SINGLE_PATTERN = Pattern.compile("/\\*([\\S\\s]+?)\\*/");
    private static final Pattern LINE_COMMENT_PATTERN_MULTIPLE_PATTERN = Pattern.compile("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)");

    @Override
    public String removeComments(String code) {
        try {
            Matcher matcherSingleLine = LINE_COMMENT_PATTERN_SINGLE_PATTERN.matcher(code);
            return LINE_COMMENT_PATTERN_MULTIPLE_PATTERN
                    .matcher(
                            matcherSingleLine.replaceAll("")
                    )
                    .replaceAll("");
        } catch (Error e) {
            LOG.error(e.getMessage(), e);
        }
        return "";
    }
}
