package io.integrityinspector.antlr.core;

import io.integrityinspector.antlr.model.CodeTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeTreeParserImpl implements CodeTreeParser {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodeTreeParserImpl.class);

    public CodeTree parseCodeTree(String fileName, String content, CodeTreeNodeConverter converter) {
        CodeTree codeTree = null;
        try {
            codeTree = converter.convertToCodeTreeNode(content);
        } catch (Exception e) {
            LOGGER.error("Failed while parse code tree for file " + fileName + ". Error: " + e.getMessage());
        }
        return codeTree;
    }
}
