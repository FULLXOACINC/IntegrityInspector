package org.plagiarism.model.filecheker;

import lombok.Getter;
import lombok.Setter;
import org.plagiarism.model.CheckLine;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class FileStringCheck extends FileCheck {
    private List<CheckLine> checkedLines;
    private BigDecimal uniqueStringPresent;

    public FileStringCheck(String codeFileName, List<CheckLine> checkedLines, BigDecimal uniqueStringPresent) {
        super(codeFileName);
        this.checkedLines = checkedLines;
        this.uniqueStringPresent = uniqueStringPresent;
    }
}