package io.integrityinspector.parser.reader.file;

import io.integrityinspector.parser.cleaner.line.LineCleaner;

import java.util.Set;

public class DefaultLineValidator implements LineValidator {

    private final Set<String> lineStartSet;
    private final LineCleaner lineCleaner;

    public DefaultLineValidator(Set<String> lineStartSet, LineCleaner lineCleaner) {
        this.lineStartSet = lineStartSet;
        this.lineCleaner = lineCleaner;
    }

    @Override
    public boolean isLineNeedAddToCheckList(String line) {
        String filtered = lineCleaner.cleanLine(line);
        if (filtered.isEmpty()) {
            return false;
        }
        return !lineStartSet
                .stream()
                .map(filtered::startsWith)
                .reduce((x, y) -> x || y)
                .orElse(false);
    }
}
