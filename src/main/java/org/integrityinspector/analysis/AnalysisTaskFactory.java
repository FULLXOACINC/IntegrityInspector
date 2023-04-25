package org.integrityinspector.analysis;

import lombok.AllArgsConstructor;
import org.integrityinspector.checker.FileChecker;
import org.integrityinspector.checker.ProjectChecker;
import org.integrityinspector.model.Project;
import org.integrityinspector.model.filecheker.FileCheck;

import java.util.List;

@AllArgsConstructor
public class AnalysisTaskFactory<T extends FileCheck, K extends FileChecker<T>> {
    private ProjectChecker<T, K> fullProjectChecker;

    public AnalysisTask<T, K> createTask(List<Project> chunk, Project checkProject) {

        return new AnalysisTask<>(fullProjectChecker, chunk, checkProject);
    }
}
