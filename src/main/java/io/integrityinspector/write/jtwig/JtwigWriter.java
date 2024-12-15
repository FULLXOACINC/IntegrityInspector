package io.integrityinspector.write.jtwig;

import io.integrityinspector.model.Analysis;
import io.integrityinspector.write.core.AnalysisWriter;
import lombok.AllArgsConstructor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@AllArgsConstructor
public class JtwigWriter implements AnalysisWriter {
    private static final Logger LOG = LoggerFactory.getLogger(JtwigWriter.class);
    private String templateName;

    public void write(Analysis analysis, String name) throws IOException {
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
