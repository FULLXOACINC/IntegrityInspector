package org.plagiarism.antlr.core;

import org.plagiarism.antlr.model.CodeTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeTreeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeTreeUtil.class);

    public static CodeTree parseCodeTree(String fileName, String commentFilteredFileContext, CodeTreeNodeConverter converter) {
        CodeTree codeTree = null;
        try {
            codeTree = converter.convertToCodeTreeNode(commentFilteredFileContext);
        } catch (Exception e) {
            LOGGER.error("Failed while parse code tree for file " + fileName + ". Error: " + e.getMessage());
        }
        return codeTree;
    }
}
