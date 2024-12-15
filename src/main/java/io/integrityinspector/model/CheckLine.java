package io.integrityinspector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class CheckLine {
    private Line line;
    private List<LineInfo> similarLines;
}