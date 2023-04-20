package org.integrityinspector.jtwig;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.integrityinspector.model.Analysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JtwigWriter {

    public void write(Analysis analysis, String name) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("analysis.twig");
        JtwigModel model = JtwigModel.newModel().with("report", analysis);
        String body = template.render(model);
        BufferedWriter writerHtml = new BufferedWriter(new FileWriter("report_" + name + ".html"));
        writerHtml.write(body);
        writerHtml.flush();
        writerHtml.close();
    }
}
