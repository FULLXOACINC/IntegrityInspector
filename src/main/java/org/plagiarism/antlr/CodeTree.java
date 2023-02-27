package org.plagiarism.antlr;

import com.github.tmatek.zhangshasha.TreeNode;
import com.github.tmatek.zhangshasha.TreeOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CodeTree implements TreeNode {

    private final String label;

    private final List<CodeTree> children = new ArrayList<>();

    private CodeTree parent;

    public void setParent(CodeTree parent) {
        this.parent = parent;
    }

    public CodeTree(String label) {
        this.label = label;
    }

    @Override
    public List<CodeTree> getChildren() {
        return this.children;
    }

    @Override
    public CodeTree getParent() {
        return this.parent;
    }

    @Override
    public int positionOfChild(TreeNode treeNode) {
        if (treeNode instanceof CodeTree) {
            CodeTree codeTree = (CodeTree) treeNode;
            int index = 0;
            for (CodeTree ch : this.children) {
                if (ch.equals(codeTree)) {
                    return index;
                }
                index++;
            }
        }
        return 0;
    }

    @Override
    public int getTransformationCost(TreeOperation operation, TreeNode other) {
        switch (operation) {
            case OP_DELETE_NODE:
            case OP_INSERT_NODE:
                return 1;

            default:
                return this.label.equals(((CodeTree) other).label) ? 0 : 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeTree that = (CodeTree) o;
        return Objects.equals(label, that.label) && Objects.equals(children, that.children) && Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, children, parent);
    }
}
