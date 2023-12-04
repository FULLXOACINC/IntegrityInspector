package io.integrityinspector.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Project {
    private String name;
    private List<CodeFile> codeFileList;
    private int projectLineCount;
}
