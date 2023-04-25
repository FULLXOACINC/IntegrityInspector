package io.integrityinspector.parser.cleaner.comment;

public class PythonFileCommentCleaner implements CommentCleaner {
    private static final String SINGLE_LINE_COMMENT_PATTERN = "(?m)^\\s*#.*$";
    private static final String MULTI_LINE_COMMENT_PATTERN = "(['\"])\\1\\1[\\d\\D]*?\\1{3}";

    @Override
    public String removeComments(String pythonCode) {
        return pythonCode
                .replaceAll(SINGLE_LINE_COMMENT_PATTERN, "")
                .replaceAll(MULTI_LINE_COMMENT_PATTERN, "");
    }
}
