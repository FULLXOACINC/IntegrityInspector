package org.integrityinspector.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Line {
    private int lineIndex;
    private String content;
    private String contentFiltered;
}
