package org.integrityinspector.jtwig;

import org.integrityinspector.config.AppConfig;

public class JtwigTemplateNameFactory {

    public String createTemplateName(AppConfig config) {
        return config.getParseCodeConfig().getNeedParseTree() ? "analysis_tree.twig" : "analysis.twig";
    }
}
