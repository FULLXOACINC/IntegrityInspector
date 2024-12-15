package io.integrityinspector.app;

import io.integrityinspector.analysis.AnalysisCreator;
import io.integrityinspector.config.AppConfig;
import io.integrityinspector.parser.reader.project.ProjectParser;
import io.integrityinspector.write.core.AnalysisWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppCoreComponents {
    ProjectParser projectParser;
    AnalysisCreator analysisCreator;
    AnalysisWriter analysisWriter;
    AppConfig config;
}
