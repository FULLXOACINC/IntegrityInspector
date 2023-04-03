package org.plagiarism.checker;

import com.github.tmatek.zhangshasha.TreeDistance;
import org.plagiarism.antlr.model.CodeTree;

public class TreeSimilarityCalculator {

    public int calculateTreeSimilarity(CodeTree firstTree, CodeTree secondTree) {
        if (firstTree == null || secondTree == null) {
            return Integer.MAX_VALUE;
        }
        return TreeDistance.treeDistanceZhangShasha(firstTree, secondTree);
    }
}
