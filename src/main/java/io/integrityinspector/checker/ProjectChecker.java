package io.integrityinspector.checker;

import io.integrityinspector.model.CodeFile;
import io.integrityinspector.model.Project;
import io.integrityinspector.model.filecheker.FileCheck;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProjectChecker<K extends FileCheck, T extends FileChecker<K>> {
    private T checker;

    public List<K> checkProject(Project checkProject, List<Project> baselineProjects) {
        List<K> fileChecks = new ArrayList<>();
        for (CodeFile codeFile : checkProject.getCodeFileList()) {
            fileChecks.add(checker.checkFile(codeFile, baselineProjects));
        }
        return fileChecks;
    }
}
