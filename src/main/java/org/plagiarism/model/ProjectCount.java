package org.plagiarism.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;

@Data
@AllArgsConstructor
public class ProjectCount {
    public static final Comparator<ProjectCount> PROJECT_COUNT_COMPARATOR = Comparator.comparing(ProjectCount::getCount).reversed();
    private String name;
    private Integer count;
}
