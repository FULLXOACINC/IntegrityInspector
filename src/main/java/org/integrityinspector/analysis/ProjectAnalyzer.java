package org.integrityinspector.analysis;

import org.integrityinspector.model.Project;
import org.integrityinspector.model.filecheker.FileCheck;

import java.util.List;

public interface ProjectAnalyzer<T extends FileCheck> {
    List<T> process(Project checkProject, List<Project> list);
}
