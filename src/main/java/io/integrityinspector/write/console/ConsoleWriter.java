package io.integrityinspector.write.console;

import io.integrityinspector.model.Analysis;
import io.integrityinspector.write.core.AnalysisWriter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@AllArgsConstructor
public class ConsoleWriter implements AnalysisWriter {
    private static final Logger LOG = LoggerFactory.getLogger(ConsoleWriter.class);

    public void write(Analysis analysis, String name) throws IOException {
        LOG.info("Report generated : {}", analysis.toString());
    }
}
