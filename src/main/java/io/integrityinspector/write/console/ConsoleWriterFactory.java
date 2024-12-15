package io.integrityinspector.write.console;

import io.integrityinspector.config.AppConfig;
import io.integrityinspector.write.core.AnalysisWriter;
import io.integrityinspector.write.core.AnalysisWriterFactory;

public class ConsoleWriterFactory implements AnalysisWriterFactory {
    @Override
    public AnalysisWriter createAnalysisWriter(AppConfig config) {
        return new ConsoleWriter();
    }
}
