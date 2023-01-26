package org.plagiarism.parser.cleaner.line;

public class DefaultLineCleaner {
    private static final String LINE_CLEAR_REGEX = "[^a-zA-Z0-9]";

    public String clearLine(String line) {
        return line.replaceAll(LINE_CLEAR_REGEX, "");
    }
}
