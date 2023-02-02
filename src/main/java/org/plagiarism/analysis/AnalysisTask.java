package org.plagiarism.analysis;

import lombok.AllArgsConstructor;
import org.plagiarism.checker.ProjectChecker;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.FileCheck;
import org.plagiarism.model.Project;

import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor
public class AnalysisTask implements Callable<List<FileCheck>> {
    private final AnalysisConfig config;
    private final List<Project> baselineList;
    private final Project check;

    @Override
    public List<FileCheck> call() {
        ProjectChecker checker = new ProjectChecker(config);
        return checker.checkProject(check, baselineList);
    }
}
