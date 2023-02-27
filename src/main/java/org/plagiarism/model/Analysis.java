package org.plagiarism.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.plagiarism.model.filecheker.FileStringCheck;
import org.plagiarism.model.filecheker.FileTreeCheck;

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
