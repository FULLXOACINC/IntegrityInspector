package org.plagiarism.cheker;

import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.text.similarity.JaccardDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.plagiarism.model.Check;
import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Line;

import java.util.ArrayList;
import java.util.List;

public class PlagiarismLineChecker {

    public List<Check> check(Line checkingLine, CodeFile baselineCodeFile, String name, String sourceFile) {
        List<Check> list = new ArrayList<>();
        for (Line baseLine : baselineCodeFile.getCode()) {
            String lineStr = checkingLine.getContentFiltered();
            String baseLineStr = baseLine.getContentFiltered();
            if (Math.abs(lineStr.length() - baseLineStr.length()) < 15) {
                double levenshteinDistance = ((double) LevenshteinDistance.getDefaultInstance().apply(lineStr, baseLineStr)) / Math.max(lineStr.length(), baseLineStr.length());
                double cosineDistance = new CosineDistance().apply(lineStr, baseLineStr);
                double jaccardDistance = new JaccardDistance().apply(lineStr, baseLineStr);
                if (levenshteinDistance < 0.333) {
                    list.add(new Check(name, sourceFile, checkingLine, baseLine, levenshteinDistance, cosineDistance, jaccardDistance));
                }
            }
        }

        return list;
    }
}
