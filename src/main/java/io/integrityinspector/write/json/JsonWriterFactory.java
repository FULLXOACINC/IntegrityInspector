package io.integrityinspector.write.json;

import io.integrityinspector.config.AppConfig;
import io.integrityinspector.write.core.AnalysisWriter;
import io.integrityinspector.write.core.AnalysisWriterFactory;

public class JsonWriterFactory implements AnalysisWriterFactory {
    @Override
    public AnalysisWriter createAnalysisWriter(AppConfig config) {
        return new JsonWriter();
    }
}
