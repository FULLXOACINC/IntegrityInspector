package org.plagiarism.checker;

import lombok.AllArgsConstructor;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.CodeFile;
import org.plagiarism.model.FileCheck;
import org.plagiarism.model.Project;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ProjectChecker {
    private final AnalysisConfig config;

    public List<FileCheck> checkProject(Project checkProject, List<Project> baselineProjects) {
        PlagiarismLineChecker plagiarismLineChecker = new PlagiarismLineChecker(config);
        FileChecker fileChecker = new FileChecker(plagiarismLineChecker, config);

        List<FileCheck> fileChecks = new ArrayList<>();
        for (CodeFile codeFile : checkProject.getCodeFileList()) {
            fileChecks.add(fileChecker.checkFile(codeFile, baselineProjects));
        }
        return fileChecks;
    }
}
