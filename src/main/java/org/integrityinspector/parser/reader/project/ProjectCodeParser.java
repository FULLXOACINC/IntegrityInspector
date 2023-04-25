package org.integrityinspector.parser.reader.project;

import org.integrityinspector.config.ParserConfig;
import org.integrityinspector.model.CodeFile;
import org.integrityinspector.parser.reader.file.CodeReader;
import org.integrityinspector.parser.reader.file.CodeReaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectCodeParser {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectCodeParser.class);
    private final CodeReaderFactory codeReaderFactory;

    public ProjectCodeParser(ParserConfig parseCodeConfig) {
        codeReaderFactory = new CodeReaderFactory(parseCodeConfig.getAdditionalFileExtensions(), parseCodeConfig.getNeedParseTree());
    }

    public List<CodeFile> parseCode(File dir) throws IOException {
        List<String> files = new ArrayList<>();
        List<CodeFile> result = new ArrayList<>();
        collectAllFiles(dir, files);
        for (String element : files) {
            String[] arr = element.split("\\.");
            String fileExtension = arr[arr.length - 1];
            CodeReader reader = codeReaderFactory.findCodeReader(fileExtension);
            result.add(reader.read(element));
        }
        return result;
    }

    private void collectAllFiles(File dir, List<String> list) {
        for (File fileEntry : Objects.requireNonNull(dir.listFiles())) {
            if (fileEntry.isDirectory()) {
                collectAllFiles(fileEntry, list);
            } else {
                LOG.info("Find new file: {}", fileEntry.getName());
                list.add(fileEntry.getAbsolutePath());
            }
        }
    }


}
