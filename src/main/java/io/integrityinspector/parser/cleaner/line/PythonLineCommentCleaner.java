package io.integrityinspector.parser.cleaner.line;

import java.util.regex.Pattern;

public class PythonLineCommentCleaner extends DefaultLineCleaner {
    private static final Pattern SINGLE_LINE_COMMENT_PATTERN = Pattern.compile("(?<!\"|')#.*$");

    @Override
    public String cleanLine(String pythonCode) {
        String line = SINGLE_LINE_COMMENT_PATTERN
                .matcher(pythonCode)
                .replaceAll("");
        return super.cleanLine(line);
    }
}
