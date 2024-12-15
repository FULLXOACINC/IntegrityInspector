package io.integrityinspector.app;

import io.integrityinspector.analysis.AnalysisCreator;
import io.integrityinspector.analysis.AnalysisCreatorFactory;
import io.integrityinspector.config.AppConfig;
import io.integrityinspector.parser.reader.project.ProjectParser;
import io.integrityinspector.parser.reader.project.ProjectParserFactory;
import io.integrityinspector.write.core.AnalysisWriter;
import io.integrityinspector.write.core.AnalysisWriterFactory;
import io.integrityinspector.write.core.AnalysisWriterFactoryImpl;

public class AppCoreComponentsFactory {


    public AppCoreComponents createAppCoreComponents(AppConfig config) {
        AnalysisCreatorFactory analysisCreatorFactory = new AnalysisCreatorFactory();
        AnalysisCreator analysisCreator = analysisCreatorFactory.createAnalysisCreator(config);

        ProjectParserFactory projectParserFactory = new ProjectParserFactory();
        ProjectParser projectParser = projectParserFactory.createProjectParser(config);

        AnalysisWriterFactory analysisWriterFactory = new AnalysisWriterFactoryImpl();
        AnalysisWriter analysisWriter = analysisWriterFactory.createAnalysisWriter(config);

        return new AppCoreComponents(
                projectParser,
                analysisCreator,
                analysisWriter,
                config
        );
    }
}
