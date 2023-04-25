package org.integrityinspector.analysis;

import lombok.AllArgsConstructor;
import org.integrityinspector.checker.FileChecker;
import org.integrityinspector.checker.ProjectChecker;
import org.integrityinspector.model.Project;
import org.integrityinspector.model.filecheker.FileCheck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Callable;

@AllArgsConstructor
public class AnalysisTask<T extends FileCheck, K extends FileChecker<T>> implements Callable<List<T>> {
    private static final Logger LOG = LoggerFactory.getLogger(AnalysisTask.class);
    private final ProjectChecker<T, K> fullProjectChecker;
    private final List<Project> baselineList;
    private final Project check;

    @Override
    public List<T> call() {
        LOG.info("Processing analysis in multi-thread, batch: {}", baselineList.stream().map(Project::getName).reduce((x, y) -> x + ", " + y).orElse(" - "));

        return fullProjectChecker.checkProject(check, baselineList);
    }
}
