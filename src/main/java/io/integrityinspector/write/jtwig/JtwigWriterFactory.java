package io.integrityinspector.write.jtwig;

import io.integrityinspector.config.AppConfig;
import io.integrityinspector.write.core.AnalysisWriter;
import io.integrityinspector.write.core.AnalysisWriterFactory;

public class JtwigWriterFactory implements AnalysisWriterFactory {
    @Override
    public AnalysisWriter createAnalysisWriter(AppConfig config) {
        JtwigTemplateNameFactory jtwigTemplateNameFactory = new JtwigTemplateNameFactory();
        return new JtwigWriter(jtwigTemplateNameFactory.createTemplateName(config));
    }
}
