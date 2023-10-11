package io.integrityinspector.parser.cleaner.line;

import java.util.regex.Pattern;

public class DefaultLineCleaner implements LineCleaner {
    //This constant RegEx use for remove all characters in the input string except for numbers, Latin letters
    private static final Pattern LINE_CLEAR_REGEX_PATTERN = Pattern.compile("[^a-zA-Z0-9]");

    @Override
    public String cleanLine(String line) {
        return LINE_CLEAR_REGEX_PATTERN
                .matcher(line)
                .replaceAll("");
    }
}
