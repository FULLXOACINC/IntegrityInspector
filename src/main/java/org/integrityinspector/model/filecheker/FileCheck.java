package org.integrityinspector.model.filecheker;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.integrityinspector.model.CheckLine;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class FileCheck {
    private String codeFileName;
    private List<CheckLine> checkedLines;
    private BigDecimal uniqueStringPresent;
}