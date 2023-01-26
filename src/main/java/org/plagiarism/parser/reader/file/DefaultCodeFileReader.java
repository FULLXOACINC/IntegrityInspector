package org.plagiarism.parser.reader.file;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Data
@AllArgsConstructor
public class DefaultCodeFileReader {
    private String lineDelimiter;

    public String readFileFullContext(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder fileContentBuffer = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            fileContentBuffer.append(line).append(lineDelimiter);
        }
        reader.close();
        return fileContentBuffer.toString();
    }
}
