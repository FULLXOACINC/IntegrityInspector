package io.integrityinspector.parser.reader.project;

import io.integrityinspector.model.CodeFile;
import io.integrityinspector.parser.reader.file.CodeReader;
import io.integrityinspector.parser.reader.file.CodeReaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ProjectCodeParserImpl implements ProjectCodeParser {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectCodeParserImpl.class);
    private final CodeReaderFactory<? extends CodeFile> codeReaderFactory;
    private final List<String> listOfSupportedExtensions;

    public ProjectCodeParserImpl(CodeReaderFactory<? extends CodeFile> codeReaderFactory, List<String> listOfSupportedExtensions) {
        this.codeReaderFactory = codeReaderFactory;
        this.listOfSupportedExtensions = listOfSupportedExtensions != null ? listOfSupportedExtensions : Collections.emptyList();
    }

    public List<CodeFile> parseCode(File dir) throws IOException {
        List<String> files = new ArrayList<>();
        List<CodeFile> result = new ArrayList<>();
        collectAllFiles(dir, files);
        for (String element : files) {
            String[] arr = element.split("\\.");
            String fileExtension = arr[arr.length - 1];
            if (listOfSupportedExtensions.contains(fileExtension) || listOfSupportedExtensions.isEmpty()) {
                CodeReader<? extends CodeFile> reader = codeReaderFactory.findCodeReader(fileExtension);
                result.add(reader.read(element));
            }
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
