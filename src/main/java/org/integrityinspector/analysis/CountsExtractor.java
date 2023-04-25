package org.integrityinspector.analysis;

import org.integrityinspector.model.ProjectCount;
import org.integrityinspector.model.filecheker.FileCheck;

import java.util.List;

public interface CountsExtractor {
    <T extends FileCheck> List<ProjectCount> extractCountsPerProject(List<T> fileChecks);
}
