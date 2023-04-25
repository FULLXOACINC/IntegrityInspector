package org.integrityinspector.jtwig;

import org.integrityinspector.model.Analysis;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JtwigWriter {
    private static final Logger LOG = LoggerFactory.getLogger(JtwigWriter.class);

    public void write(Analysis analysis, String name, String templateName) throws IOException {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(templateName);
        JtwigModel model = JtwigModel.newModel().with("report", analysis);
        String body = template.render(model);
        String fileName = "report_" + name + ".html";
        BufferedWriter writerHtml = new BufferedWriter(new FileWriter(fileName));
        writerHtml.write(body);
        writerHtml.flush();
        writerHtml.close();
        LOG.info("Report generated : {}", fileName);
    }
}
