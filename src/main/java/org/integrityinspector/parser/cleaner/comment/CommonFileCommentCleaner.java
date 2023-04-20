package org.integrityinspector.parser.cleaner.comment;

public class CommonFileCommentCleaner implements CommentCleaner {
    private static final String LINE_COMMENT_PATTERN = "(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)";

    @Override
    public String removeComments(String javaCode) {
        return javaCode
                .replaceAll(LINE_COMMENT_PATTERN, "");
    }
}
