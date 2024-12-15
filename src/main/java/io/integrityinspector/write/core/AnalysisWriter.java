package io.integrityinspector.write.core;

import io.integrityinspector.model.Analysis;

import java.io.IOException;

public interface AnalysisWriter {
    void write(Analysis analysis, String name) throws IOException;
}
