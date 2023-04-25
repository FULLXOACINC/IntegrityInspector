package io.integrityinspector.model.filecheker;

import io.integrityinspector.model.CheckLine;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class FileCheck {
    private String codeFileName;
    private List<CheckLine> checkedLines;
    private BigDecimal uniqueStringPresent;
}