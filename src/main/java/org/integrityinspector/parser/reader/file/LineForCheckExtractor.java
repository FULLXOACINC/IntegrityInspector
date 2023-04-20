package org.integrityinspector.parser.reader.file;

import org.integrityinspector.model.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LineForCheckExtractor {

    List<Line> extractLinesForCheck(String fileContext,
                                    String lineDelimiter,
                                    Function<String, Boolean> isLineNeedAddToCheckListFunction,
                                    Function<String, String> lineCleaner) {
        List<Line> linesForCheck = new ArrayList<>();
        int index = 0;
        for (String line : fileContext.split(lineDelimiter)) {
            index++;
            if (isLineNeedAddToCheckListFunction.apply(line)) {
                linesForCheck.add(new Line(index, line, lineCleaner.apply(line)));
            }
        }
        return linesForCheck;
    }
}