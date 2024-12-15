package io.integrityinspector.write.core;

import io.integrityinspector.config.AppConfig;

public interface AnalysisWriterFactory {

    AnalysisWriter createAnalysisWriter(AppConfig config);
}
