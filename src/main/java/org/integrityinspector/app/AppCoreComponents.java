package org.integrityinspector.app;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.integrityinspector.analysis.AnalysisCreator;
import org.integrityinspector.parser.reader.project.ProjectParser;

@AllArgsConstructor
@Getter
public class AppCoreComponents {
    ProjectParser projectParser;
    AnalysisCreator analysisCreator;

    String reportTemplate;
}
