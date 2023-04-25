package io.integrityinspector.checker;

import com.github.tmatek.zhangshasha.TreeDistance;
import io.integrityinspector.antlr.model.CodeTree;

public class TreeSimilarityCalculator {

    public int calculateTreeSimilarity(CodeTree firstTree, CodeTree secondTree) {
        if (firstTree == null || secondTree == null) {
            return Integer.MAX_VALUE;
        }
        return TreeDistance.treeDistanceZhangShasha(firstTree, secondTree);
    }
}
