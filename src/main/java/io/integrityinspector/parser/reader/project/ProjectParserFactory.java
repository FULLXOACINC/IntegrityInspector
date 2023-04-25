package io.integrityinspector.parser.reader.project;

import io.integrityinspector.config.AppConfig;

public class ProjectParserFactory {
    public ProjectParser createProjectParser(AppConfig config) {
        return new ProjectParserImpl(config.getParseCodeConfig());
    }
}
