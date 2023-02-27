package org.plagiarism.checker;

import lombok.AllArgsConstructor;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Project;
import org.plagiarism.model.filecheker.FileCheck;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProjectChecker<K extends FileCheck, T extends FileChecker<K>> {
    private final AnalysisConfig config;
    private T checker;

    public List<K> checkProject(Project checkProject, List<Project> baselineProjects) {
        List<K> fileChecks = new ArrayList<>();
        for (CodeFile codeFile : checkProject.getCodeFileList()) {
            fileChecks.add(checker.checkFile(codeFile, baselineProjects));
        }
        return fileChecks;
    }
}
