package io.integrityinspector.parser.reader.file;

public interface LineValidator {
    boolean isLineNeedAddToCheckList(String line);
}
