package io.integrityinspector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Line {
    private int lineIndex;
    private String content;
    private String contentFiltered;
}
