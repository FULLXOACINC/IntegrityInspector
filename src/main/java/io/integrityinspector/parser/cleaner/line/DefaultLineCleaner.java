package io.integrityinspector.parser.cleaner.line;

public class DefaultLineCleaner implements LineCleaner {
    //This constant RegEx use for remove all characters in the input string except for numbers, Latin letters
    private static final String LINE_CLEAR_REGEX = "[^a-zA-Z0-9]";

    @Override
    public String cleanLine(String line) {
        return line.replaceAll(LINE_CLEAR_REGEX, "");
    }
}
