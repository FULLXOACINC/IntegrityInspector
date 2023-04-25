package org.integrityinspector.parser.reader.project;

import org.integrityinspector.config.AppConfig;

public class ProjectParserFactory {
    public ProjectParser createProjectParser(AppConfig config) {
        return new ProjectParserImpl(config.getParseCodeConfig());
    }
}
