package io.integrityinspector.analysis;

import io.integrityinspector.checker.FileChecker;
import io.integrityinspector.checker.ProjectChecker;
import io.integrityinspector.model.Project;
import io.integrityinspector.model.filecheker.FileCheck;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class AnalysisTaskFactory<T extends FileCheck, K extends FileChecker<T>> {
    private ProjectChecker<T, K> fullProjectChecker;

    public AnalysisTask<T, K> createTask(List<Project> chunk, Project checkProject) {

        return new AnalysisTask<>(fullProjectChecker, chunk, checkProject);
    }
}
