package org.plagiarism.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalysisConfig {
    private int projectLimit;
    private int lineSimilarLimit;
    private int maxLineLengthDiff;
    private int minLineLength;
    private double levenshteinSimilarityPercent;
}
