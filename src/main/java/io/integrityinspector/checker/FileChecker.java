package io.integrityinspector.checker;

import io.integrityinspector.model.CodeFile;
import io.integrityinspector.model.Project;
import io.integrityinspector.model.filecheker.FileCheck;

import java.util.List;

public interface FileChecker<T extends FileCheck> {

    T checkFile(CodeFile codeFile, List<Project> baselineProjects);
}
