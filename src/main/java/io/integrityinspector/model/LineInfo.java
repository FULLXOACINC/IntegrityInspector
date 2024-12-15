package io.integrityinspector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Comparator;

@Data
@AllArgsConstructor
@ToString
public class LineInfo {
    public static final Comparator<LineInfo> LINE_INFO_COMPARATOR = Comparator.comparing(LineInfo::getProject);

    private String project;
    private String file;
    private double levenshteinDistance;
    private double cosineDistance;
    private double jaccardDistance;
    private Line line;
}
