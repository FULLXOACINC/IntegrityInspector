package org.plagiarism.checker;

import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Project;
import org.plagiarism.model.filecheker.FileCheck;

import java.util.List;

public interface FileChecker<T extends FileCheck> {

    T checkFile(CodeFile codeFile, List<Project> baselineProjects);
}
