package io.integrityinspector.analysis;

import io.integrityinspector.model.ProjectCount;
import io.integrityinspector.model.filecheker.FileCheck;

import java.util.List;

public interface CountsExtractor {
    <T extends FileCheck> List<ProjectCount> extractCountsPerProject(List<T> fileChecks);
}
