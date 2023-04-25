package io.integrityinspector.model;

import io.integrityinspector.model.filecheker.FileCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Analysis {
    private List<? extends FileCheck> projectChecks;
    private List<ProjectCount> countPerProject;
    private BigDecimal totalUniquenessPercentage;

}
