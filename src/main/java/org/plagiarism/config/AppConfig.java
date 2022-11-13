package org.plagiarism.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppConfig {
    private AnalysisConfig analysisConfig;
    private ParserConfig parseCodeConfig;
}
