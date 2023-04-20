package org.integrityinspector.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CheckLine {
    private Line line;
    private List<LineInfo> similarLines;
}