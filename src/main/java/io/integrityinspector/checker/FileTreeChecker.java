package io.integrityinspector.checker;

import io.integrityinspector.antlr.model.CodeTree;
import io.integrityinspector.model.CodeFile;
import io.integrityinspector.model.Project;
import io.integrityinspector.model.TreeSimilarity;
import io.integrityinspector.model.filecheker.FileCheck;
import io.integrityinspector.model.filecheker.FileTreeCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FileTreeChecker implements FileChecker<FileTreeCheck> {
    private static final TreeSimilarityCalculator TREE_SIMILARITY_CALCULATOR = new TreeSimilarityCalculator();
    private final FileChecker<FileCheck> fileStringChecker;

    public FileTreeChecker(FileChecker<FileCheck> fileStringChecker) {
        this.fileStringChecker = fileStringChecker;
    }

    public FileTreeCheck checkFile(CodeFile codeFile, List<Project> baselineProjects) {
        FileCheck fileCheck = fileStringChecker.checkFile(codeFile, baselineProjects);
        List<TreeSimilarity> codeTreeSimilarityList = calculateTreeSimilarity(codeFile, baselineProjects);

        return new FileTreeCheck(
                codeFile.getSourceFile(),
                fileCheck.getCheckedLines(),
                fileCheck.getUniqueStringPresent(),
                codeTreeSimilarityList
        );
    }


    private List<TreeSimilarity> calculateTreeSimilarity(CodeFile codeFile, List<Project> baselineProjects) {
        List<TreeSimilarity> result = new ArrayList<>();
        CodeTree codeFileTree = codeFile.getCodeTree();
        for (Project baselineProject : baselineProjects) {
            for (CodeFile baselineCodeFile : baselineProject.getCodeFileList()) {
                if (Objects.equals(codeFile.getLanguage(), baselineCodeFile.getLanguage())) {
                    CodeTree baselineCodeFileTree = baselineCodeFile.getCodeTree();
                    int similarity = TREE_SIMILARITY_CALCULATOR.calculateTreeSimilarity(codeFileTree, baselineCodeFileTree);
                    result.add(new TreeSimilarity(baselineProject.getName(), baselineCodeFile.getSourceFile(), similarity));
                }
            }
        }
        return result;
    }
}
