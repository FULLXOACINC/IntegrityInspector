package org.plagiarism.checker;

import com.github.tmatek.zhangshasha.TreeDistance;
import org.plagiarism.antlr.CodeTree;

public class TreeSimilarityCalculator {

    public int calculateTreeSimilarity(CodeTree firstTree, CodeTree secondTree) {
        if (firstTree == null || secondTree == null) {
            return 9999;
        }
        return TreeDistance.treeDistanceZhangShasha(firstTree, secondTree);
    }
}
