package org.integrityinspector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.integrityinspector.model.filecheker.FileCheck;

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
