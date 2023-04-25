package io.integrityinspector.analysis;

import io.integrityinspector.model.Analysis;
import io.integrityinspector.model.Project;

import java.util.List;

public interface AnalysisCreator {
    Analysis create(Project checkProject, List<Project> baselineProjects);
}
