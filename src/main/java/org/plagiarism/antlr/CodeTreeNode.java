package org.plagiarism.antlr;

import com.github.tmatek.zhangshasha.TreeNode;
import com.github.tmatek.zhangshasha.TreeOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CodeTreeNode implements TreeNode {

    private String label;

    private List<CodeTreeNode> children = new ArrayList<>();

    private CodeTreeNode parent;

    public void setParent(CodeTreeNode parent) {
        this.parent = parent;
    }

    public CodeTreeNode(String label) {
        this.label = label;
    }

    @Override
    public List<CodeTreeNode> getChildren() {
        return this.children;
    }

    @Override
    public CodeTreeNode getParent() {
        return this.parent;
    }

    @Override
    public int positionOfChild(TreeNode treeNode) {
        if (treeNode instanceof CodeTreeNode) {
            CodeTreeNode codeTreeNode = (CodeTreeNode) treeNode;
            int index = 0;
            for (CodeTreeNode ch : this.children) {
                if (ch.equals(codeTreeNode)) {
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
                return this.label.equals(((CodeTreeNode) other).label) ? 0 : 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodeTreeNode that = (CodeTreeNode) o;
        return Objects.equals(label, that.label) && Objects.equals(children, that.children) && Objects.equals(parent, that.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, children, parent);
    }
}
