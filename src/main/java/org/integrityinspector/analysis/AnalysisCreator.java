package org.integrityinspector.analysis;

import org.integrityinspector.model.Analysis;
import org.integrityinspector.model.Project;

import java.util.List;

public interface AnalysisCreator {
    Analysis create(Project checkProject, List<Project> baselineProjects);
}
