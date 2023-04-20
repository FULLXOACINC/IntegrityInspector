package org.integrityinspector.model.filecheker;

import lombok.Getter;
import lombok.Setter;
import org.integrityinspector.model.CheckLine;
import org.integrityinspector.model.TreeSimilarity;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class FileFullCheck extends FileCheck {

    private List<CheckLine> checkedLines;
    private BigDecimal uniqueStringPresent;
    private List<TreeSimilarity> codeTreeSimilarityList;

    public FileFullCheck(String codeFileName, List<CheckLine> checkedLines, BigDecimal uniqueStringPresent, List<TreeSimilarity> codeTreeSimilarityList) {
        super(codeFileName);
        this.checkedLines = checkedLines;
        this.uniqueStringPresent = uniqueStringPresent;
        this.codeTreeSimilarityList = codeTreeSimilarityList;
    }
}