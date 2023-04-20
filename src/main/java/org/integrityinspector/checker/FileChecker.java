package org.integrityinspector.checker;

import org.integrityinspector.model.CodeFile;
import org.integrityinspector.model.Project;
import org.integrityinspector.model.filecheker.FileCheck;

import java.util.List;

public interface FileChecker<T extends FileCheck> {

    T checkFile(CodeFile codeFile, List<Project> baselineProjects);
}
