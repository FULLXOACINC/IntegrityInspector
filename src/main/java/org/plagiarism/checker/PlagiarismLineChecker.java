package org.plagiarism.checker;

import lombok.AllArgsConstructor;
import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.text.similarity.JaccardDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.Check;
import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Line;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class PlagiarismLineChecker {
    private AnalysisConfig config;

    public List<Check> check(Line checkingLine, CodeFile baselineCodeFile, String name, String sourceFile) {
        List<Check> list = new ArrayList<>();
        for (Line baseLine : baselineCodeFile.getCode()) {
            String lineStr = checkingLine.getContentFiltered();
            String baseLineStr = baseLine.getContentFiltered();
            int strLengthDiff = Math.abs(lineStr.length() - baseLineStr.length());
            int minLength = Math.min(lineStr.length(), baseLineStr.length());
            if (strLengthDiff < config.getMaxLineLengthDiff() && minLength > config.getMinLineLength()) {
                double levenshteinSimilarity = ((double) LevenshteinDistance.getDefaultInstance().apply(lineStr, baseLineStr)) / Math.max(lineStr.length(), baseLineStr.length());
                double cosineDistance = new CosineDistance().apply(lineStr, baseLineStr);
                double jaccardDistance = new JaccardDistance().apply(lineStr, baseLineStr);
                if (levenshteinSimilarity < config.getLevenshteinSimilarityPercent()) {
                    list.add(new Check(name, sourceFile, checkingLine, baseLine, levenshteinSimilarity, cosineDistance, jaccardDistance));
                }
            }
        }

        return list;
    }
}
