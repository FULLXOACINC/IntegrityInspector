package org.integrityinspector.analysis;

import lombok.AllArgsConstructor;
import org.integrityinspector.checker.FileFullChecker;
import org.integrityinspector.checker.PlagiarismLineChecker;
import org.integrityinspector.checker.ProjectChecker;
import org.integrityinspector.config.AnalysisConfig;
import org.integrityinspector.model.Project;
import org.integrityinspector.model.filecheker.FileFullCheck;

import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor
public class AnalysisTask implements Callable<List<FileFullCheck>> {
    private final AnalysisConfig config;
    private final List<Project> baselineList;
    private final Project check;

    @Override
    public List<FileFullCheck> call() {
        PlagiarismLineChecker plagiarismLineChecker = new PlagiarismLineChecker(config);
        FileFullChecker fileFullChecker = new FileFullChecker(plagiarismLineChecker, config);
        ProjectChecker<FileFullCheck, FileFullChecker> fullProjectChecker = new ProjectChecker<>(config, fileFullChecker);
        return fullProjectChecker.checkProject(check, baselineList);
    }
}
