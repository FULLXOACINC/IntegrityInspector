package org.plagiarism.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Analysis {
    private List<FileCheck> projectChecks;
    private List<ProjectCount> countPerProject;
}
