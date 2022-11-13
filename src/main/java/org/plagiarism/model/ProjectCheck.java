package org.plagiarism.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class ProjectCheck {
    private String codeFileName;
    private List<CheckLine> checkedLines;
}