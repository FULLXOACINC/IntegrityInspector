package org.plagiarism.analysis;

import lombok.AllArgsConstructor;
import org.plagiarism.checker.FileFullChecker;
import org.plagiarism.checker.PlagiarismLineChecker;
import org.plagiarism.checker.ProjectChecker;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.Project;
import org.plagiarism.model.filecheker.FileFullCheck;

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
