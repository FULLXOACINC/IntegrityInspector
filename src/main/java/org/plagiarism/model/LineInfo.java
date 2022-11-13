package org.plagiarism.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LineInfo {
    private String project;
    private String file;
    private double levenshteinDistance;
    private double cosineDistance;
    private double jaccardDistance;
    private Line line;
}
