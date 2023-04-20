package org.integrityinspector.parser.cleaner.comment;

public class PythonLineCommentCleaner implements CommentCleaner {
    private static final String SINGLE_LINE_COMMENT_PATTERN = "(?<!\"|\')#.*$";

    @Override
    public String removeComments(String pythonCode) {
        return pythonCode
                .replaceAll(SINGLE_LINE_COMMENT_PATTERN, "");
    }
}
