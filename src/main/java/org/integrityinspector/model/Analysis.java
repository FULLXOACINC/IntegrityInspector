package org.integrityinspector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.integrityinspector.model.filecheker.FileStringCheck;
import org.integrityinspector.model.filecheker.FileTreeCheck;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Analysis {
    private List<FileStringCheck> projectStringChecks;
    private List<FileTreeCheck> projectTreeChecks;
    private List<ProjectCount> countPerProject;
    private BigDecimal totalUniquenessPercentage;

}
