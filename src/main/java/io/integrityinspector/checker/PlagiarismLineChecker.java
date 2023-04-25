package io.integrityinspector.checker;

import io.integrityinspector.model.Check;
import io.integrityinspector.model.CodeFile;
import io.integrityinspector.model.Line;
import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.text.similarity.JaccardDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;

public class PlagiarismLineChecker {
    private final int minLineLength;
    private final int maxLineLengthDiff;
    private final double levenshteinSimilarityPercent;

    private final LevenshteinDistance levenshteinDistanceCalc = LevenshteinDistance.getDefaultInstance();
    private final JaccardDistance jaccardDistanceCalc = new JaccardDistance();

    private final CosineDistance cosineDistanceCalc = new CosineDistance();

    public PlagiarismLineChecker(int minLineLength, int maxLineLengthDiff, double levenshteinSimilarityPercent) {
        this.minLineLength = minLineLength;
        this.maxLineLengthDiff = maxLineLengthDiff;
        this.levenshteinSimilarityPercent = levenshteinSimilarityPercent;
    }


    public List<Check> check(Line checkingLine, CodeFile baselineCodeFile, String name, String sourceFile) {
        List<Check> list = new ArrayList<>();
        for (Line baseLine : baselineCodeFile.getCode()) {
            String lineStr = checkingLine.getContentFiltered();
            String baseLineStr = baseLine.getContentFiltered();
            int strLengthDiff = Math.abs(lineStr.length() - baseLineStr.length());
            int minLength = Math.min(lineStr.length(), baseLineStr.length());
            if (strLengthDiff < maxLineLengthDiff && minLength > minLineLength) {
                double levenshteinSimilarity = ((double) levenshteinDistanceCalc.apply(lineStr, baseLineStr)) / Math.max(lineStr.length(), baseLineStr.length());
                double cosineDistance = cosineDistanceCalc.apply(lineStr, baseLineStr);
                double jaccardDistance = jaccardDistanceCalc.apply(lineStr, baseLineStr);
                if (levenshteinSimilarity < levenshteinSimilarityPercent) {
                    list.add(new Check(name, sourceFile, checkingLine, baseLine, levenshteinSimilarity, cosineDistance, jaccardDistance));
                }
            }
        }

        return list;
    }
}
