package io.integrityinspector.parser.reader.file;

import io.integrityinspector.model.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DefaultLineForCheckExtractor implements LineForCheckExtractor {
    private final String lineDelimiter;

    public DefaultLineForCheckExtractor(String lineDelimiter) {
        this.lineDelimiter = lineDelimiter;
    }

    @Override
    public List<Line> extractLinesForCheck(String fileContent,
                                           Function<String, Boolean> isLineNeedAddToCheckListFunction,
                                           Function<String, String> lineCleaner) {
        List<Line> linesForCheck = new ArrayList<>();
        int index = 0;
        for (String line : fileContent.split(lineDelimiter)) {
            index++;
            if (isLineNeedAddToCheckListFunction.apply(line)) {
                linesForCheck.add(new Line(index, line, lineCleaner.apply(line)));
            }
        }
        return linesForCheck;
    }
}