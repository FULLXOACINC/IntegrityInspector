package io.integrityinspector.parser.reader.project;

import io.integrityinspector.config.AppConfig;
import io.integrityinspector.model.CodeFile;
import io.integrityinspector.parser.reader.file.CodeReaderFactory;
import io.integrityinspector.parser.reader.file.CodeReaderFactoryFactory;

public class ProjectParserFactory {
    public ProjectParser createProjectParser(AppConfig config) {
        CodeReaderFactory<? extends CodeFile> codeReaderFactory = new CodeReaderFactoryFactory().createCodeReaderFactory(config);
        ProjectCodeParser projectCodeParser = new DefaultProjectCodeParser(codeReaderFactory, config.getParseCodeConfig().getListOfSupportedExtensions());
        return new DefaultProjectParser(projectCodeParser);
    }
}
