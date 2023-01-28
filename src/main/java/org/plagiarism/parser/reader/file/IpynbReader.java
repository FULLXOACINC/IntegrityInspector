package org.plagiarism.parser.reader.file;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.plagiarism.model.CodeFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IpynbReader extends PythonReader {
    private static final String CELLS = "cells";
    private static final String CELL_TYPE = "cell_type";
    private static final String CODE = "code";
    private static final String SOURCE = "source";

    @Override
    public CodeFile read(String file) throws IOException {
        String ipynbCodeFileContent = readIpynbCodeFile(file);
        return super.convertToCodeFile(file, ipynbCodeFileContent);
    }

    private String readIpynbCodeFile(String file) throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get(file));
        JsonObject root = new Gson().fromJson(new InputStreamReader(inputStream), JsonObject.class);
        StringBuilder fileContent = new StringBuilder();
        JsonArray array = root.getAsJsonArray(CELLS);
        for (JsonElement cell : array) {
            JsonObject cellObj = cell.getAsJsonObject();
            String cellType = cellObj.get(CELL_TYPE).getAsString();
            if (CODE.equals(cellType)) {
                for (JsonElement line : cellObj.getAsJsonArray(SOURCE)) {
                    fileContent.append(line.getAsString());
                }
            }
        }
        return fileContent.toString();
    }

}
