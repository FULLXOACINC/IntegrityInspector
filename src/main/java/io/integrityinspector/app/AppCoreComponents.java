package io.integrityinspector.app;

import io.integrityinspector.analysis.AnalysisCreator;
import io.integrityinspector.parser.reader.project.ProjectParser;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppCoreComponents {
    ProjectParser projectParser;
    AnalysisCreator analysisCreator;

    String reportTemplate;
}
