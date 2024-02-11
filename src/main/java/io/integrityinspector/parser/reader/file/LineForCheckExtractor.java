package io.integrityinspector.parser.reader.file;

import io.integrityinspector.model.Line;

import java.util.List;
import java.util.function.Function;

public interface LineForCheckExtractor {
    List<Line> extractLinesForCheck(String fileContent,
                                    Function<String, Boolean> isLineNeedAddToCheckListFunction,
                                    Function<String, String> lineCleaner);
}
