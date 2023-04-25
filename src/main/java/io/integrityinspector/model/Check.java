package io.integrityinspector.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;

@Data
@AllArgsConstructor
public class Check {
    public static final Comparator<Check> CHECK_COMPARATOR = Comparator.comparing(obj -> obj.getLevenshteinDistance() + obj.getCosineDistance() + obj.getJaccardDistance());

    private String baselineProject;
    private String baselineCodeFile;
    private Line checked;
    private Line baseline;
    private double levenshteinDistance;
    private double cosineDistance;
    private double jaccardDistance;
}