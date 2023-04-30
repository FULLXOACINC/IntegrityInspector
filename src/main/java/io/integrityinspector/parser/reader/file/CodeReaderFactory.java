package io.integrityinspector.parser.reader.file;

import io.integrityinspector.config.AdditionalFileExtensionConfig;
import io.integrityinspector.model.CodeFile;

import java.util.List;
import java.util.Map;

public class CodeReaderFactory<T extends CodeFile> {

    private final CodeReader<T> defaultReader;
    private final Map<String, CodeReader<T>> readerMap;
    private final List<AdditionalFileExtensionConfig> additionalFileExtensions;

    public CodeReaderFactory(CodeReader<T> defaultReader, Map<String, CodeReader<T>> readerMap, List<AdditionalFileExtensionConfig> additionalFileExtensions) {
        this.defaultReader = defaultReader;
        this.readerMap = readerMap;
        this.additionalFileExtensions = additionalFileExtensions;
    }

    public CodeReader<T> findCodeReader(String fileExtension) {
        if (this.readerMap.containsKey(fileExtension)) {
            return this.readerMap.get(fileExtension);
        }
        for (AdditionalFileExtensionConfig config : this.additionalFileExtensions) {
            if (config.getExtensions().contains(fileExtension)) {
                return this.readerMap.getOrDefault(config.getExistingExtension(), this.defaultReader);
            }
        }
        return this.defaultReader;
    }
}
