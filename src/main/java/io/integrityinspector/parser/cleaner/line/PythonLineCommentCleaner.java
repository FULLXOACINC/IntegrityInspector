package io.integrityinspector.parser.cleaner.line;

public class PythonLineCommentCleaner extends DefaultLineCleaner {
    private static final String SINGLE_LINE_COMMENT_PATTERN = "(?<!\"|')#.*$";

    @Override
    public String cleanLine(String pythonCode) {
        return super.cleanLine(pythonCode
                .replaceAll(SINGLE_LINE_COMMENT_PATTERN, ""));
    }
}
