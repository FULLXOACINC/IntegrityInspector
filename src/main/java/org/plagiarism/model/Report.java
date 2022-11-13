package org.plagiarism.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    private List<ProjectCheck> projectCheckList;
    private List<TTT> fileCoverage;
}
