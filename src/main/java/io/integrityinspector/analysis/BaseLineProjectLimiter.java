package io.integrityinspector.analysis;

import io.integrityinspector.model.Project;
import io.integrityinspector.model.ProjectCount;

import java.util.List;

public interface BaseLineProjectLimiter {
    List<Project> limitBaselineProjectList(List<ProjectCount> countsPerProject, List<Project> baselineProjects);
}
