package io.integrityinspector.write.core;

import io.integrityinspector.config.AppConfig;
import io.integrityinspector.write.console.ConsoleWriterFactory;
import io.integrityinspector.write.json.JsonWriterFactory;

import java.util.HashMap;
import java.util.Map;

public class AnalysisWriterFactoryImpl implements AnalysisWriterFactory{
    private final Map<String, AnalysisWriterFactory> factoryMap;

    public AnalysisWriterFactoryImpl() {
        factoryMap = new HashMap<>();
        factoryMap.put("json", new JsonWriterFactory());
    }

    public AnalysisWriter createAnalysisWriter(AppConfig config) {
        AnalysisWriterFactory factory = factoryMap.getOrDefault(config.getAnalysisConfig().getReportResultFormat(), new ConsoleWriterFactory());
        return factory.createAnalysisWriter(config);

    }
}
