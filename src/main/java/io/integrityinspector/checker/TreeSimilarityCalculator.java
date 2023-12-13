package io.integrityinspector.checker;

import com.github.tmatek.zhangshasha.TreeDistance;
import io.integrityinspector.antlr.model.CodeTree;

public class TreeSimilarityCalculator {
    public static final int NOT_SUPPORT_CODE = -999;

    public int calculateTreeSimilarity(CodeTree firstTree, CodeTree secondTree) {
        if (firstTree == null || secondTree == null) {
            return Integer.MAX_VALUE;
        }
        if (firstTree.getState() == NOT_SUPPORT_CODE || secondTree.getState() == NOT_SUPPORT_CODE) {
            return Integer.MAX_VALUE;
        }
        return TreeDistance.treeDistanceZhangShasha(firstTree, secondTree);
    }
}
