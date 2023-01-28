package org.plagiarism.parser.cleaner.file;

public class CommonFileCommentCleaner {
    private static final String LINE_COMMENT_PATTERN = "(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)";

    public String removeComments(String javaCode) {
        return javaCode
                .replaceAll(LINE_COMMENT_PATTERN, "");
    }
}
