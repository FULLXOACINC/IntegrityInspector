package org.integrityinspector.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AnalysisConfig {
    private int projectLimit;
    private int lineSimilarLimit;
    private int maxLineLengthDiff;
    private int minLineLength;
    private double levenshteinSimilarityPercent;
    private int threadsCount;
}
