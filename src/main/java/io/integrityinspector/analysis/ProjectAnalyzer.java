package io.integrityinspector.analysis;

import io.integrityinspector.model.Project;
import io.integrityinspector.model.filecheker.FileCheck;

import java.util.List;

public interface ProjectAnalyzer<T extends FileCheck> {
    List<T> process(Project checkProject, List<Project> list);
}
