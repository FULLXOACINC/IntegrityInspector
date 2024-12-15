package io.integrityinspector.write.json;

import com.google.gson.Gson;
import io.integrityinspector.model.Analysis;
import io.integrityinspector.write.core.AnalysisWriter;
import io.integrityinspector.write.jtwig.JtwigWriter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@AllArgsConstructor
public class JsonWriter implements AnalysisWriter {
    private static final Logger LOG = LoggerFactory.getLogger(JtwigWriter.class);

    public void write(Analysis analysis, String name) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(analysis);
        String fileName = "report_" + name + ".json";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(json);
        writer.flush();
        writer.close();
        LOG.info("Report generated : {}", fileName);
    }
}
