package org.plagiarism.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;

@Data
@AllArgsConstructor
public class TreeSimilarity {
    public static final Comparator<TreeSimilarity> TREE_SCORE_COMPARATOR = Comparator.comparing(TreeSimilarity::getSimilarityScore);
    private String project;
    private String codeFileName;
    private int similarityScore;

}