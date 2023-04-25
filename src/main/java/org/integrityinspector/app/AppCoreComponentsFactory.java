package org.integrityinspector.app;

import org.integrityinspector.analysis.AnalysisCreator;
import org.integrityinspector.analysis.AnalysisCreatorFactory;
import org.integrityinspector.config.AppConfig;
import org.integrityinspector.jtwig.JtwigTemplateNameFactory;
import org.integrityinspector.parser.reader.project.ProjectParser;
import org.integrityinspector.parser.reader.project.ProjectParserFactory;

public class AppCoreComponentsFactory {


    public AppCoreComponents createAppCoreComponents(AppConfig config) {
        AnalysisCreatorFactory analysisCreatorFactory = new AnalysisCreatorFactory();
        AnalysisCreator analysisCreator = analysisCreatorFactory.createAnalysisCreator(config);

        JtwigTemplateNameFactory jtwigTemplateNameFactory = new JtwigTemplateNameFactory();
        String templateName = jtwigTemplateNameFactory.createTemplateName(config);

        ProjectParserFactory projectParserFactory = new ProjectParserFactory();
        ProjectParser projectParser = projectParserFactory.createProjectParser(config);

        return new AppCoreComponents(
                projectParser,
                analysisCreator,
                templateName
        );
    }
}
