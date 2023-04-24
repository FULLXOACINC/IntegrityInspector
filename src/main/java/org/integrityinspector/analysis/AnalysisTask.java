package org.integrityinspector.analysis;

import lombok.AllArgsConstructor;
import org.integrityinspector.checker.FileFullChecker;
import org.integrityinspector.checker.PlagiarismLineChecker;
import org.integrityinspector.checker.ProjectChecker;
import org.integrityinspector.config.AnalysisConfig;
import org.integrityinspector.model.Project;
import org.integrityinspector.model.filecheker.FileFullCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor
public class AnalysisTask implements Callable<List<FileFullCheck>> {
    private static final Logger LOG = LoggerFactory.getLogger(AnalysisTask.class);
    private final AnalysisConfig config;
    private final List<Project> baselineList;
    private final Project check;

    @Override
    public List<FileFullCheck> call() {
        LOG.info("Processing analysis in multi-thread, batch: {}", baselineList.stream().map(Project::getName).reduce((x, y) -> x + ", " + y).orElse(" - "));
        PlagiarismLineChecker plagiarismLineChecker = new PlagiarismLineChecker(config);
        FileFullChecker fileFullChecker = new FileFullChecker(plagiarismLineChecker, config);
        ProjectChecker<FileFullCheck, FileFullChecker> fullProjectChecker = new ProjectChecker<>(config, fileFullChecker);
        return fullProjectChecker.checkProject(check, baselineList);
    }
}
