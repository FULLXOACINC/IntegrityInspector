package org.integrityinspector.model.filecheker;

import lombok.Getter;
import lombok.Setter;
import org.integrityinspector.model.CheckLine;
import org.integrityinspector.model.TreeSimilarity;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class FileTreeCheck extends FileCheck {

    private List<TreeSimilarity> codeTreeSimilarityList;

    public FileTreeCheck(String codeFileName, List<CheckLine> checkedLines, BigDecimal uniqueStringPresent, List<TreeSimilarity> codeTreeSimilarityList) {
        super(codeFileName, checkedLines, uniqueStringPresent);
        this.codeTreeSimilarityList = codeTreeSimilarityList;
    }
}