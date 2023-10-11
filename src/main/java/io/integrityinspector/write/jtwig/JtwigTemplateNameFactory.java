package io.integrityinspector.write.jtwig;

import io.integrityinspector.config.AppConfig;

public class JtwigTemplateNameFactory {

    public String createTemplateName(AppConfig config) {
        return config.getParseCodeConfig().getNeedParseTree() ? "analysis_tree.twig" : "analysis.twig";
    }
}
