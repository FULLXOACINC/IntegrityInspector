package org.integrityinspector.analysis;

import org.integrityinspector.model.Project;
import org.integrityinspector.model.ProjectCount;

import java.util.List;

public interface BaseLineProjectLimiter {
    List<Project> limitBaselineProjectList(List<ProjectCount> countsPerProject, List<Project> baselineProjects);
}
