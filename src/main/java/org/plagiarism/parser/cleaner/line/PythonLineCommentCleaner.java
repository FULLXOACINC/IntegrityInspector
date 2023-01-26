package org.plagiarism.parser.cleaner.line;

public class PythonLineCommentCleaner {
    private static final String SINGLE_LINE_COMMENT_PATTERN = "(?<!\"|\')#.*$";

    public String removeComments(String pythonCode) {
        return pythonCode
                .replaceAll(SINGLE_LINE_COMMENT_PATTERN, "");
    }
}
