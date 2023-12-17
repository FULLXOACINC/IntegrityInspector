package io.integrityinspector.checker;

import io.integrityinspector.antlr.model.CodeTree;
import io.integrityinspector.model.CodeFile;
import io.integrityinspector.model.CodeFileTree;
import io.integrityinspector.model.Project;
import io.integrityinspector.model.TreeSimilarity;
import io.integrityinspector.model.filecheker.FileCheck;
import io.integrityinspector.model.filecheker.FileTreeCheck;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class FileTreeChecker implements FileChecker<FileTreeCheck> {
    private final TreeSimilarityCalculator treeSimilarityCalculator;
    private final FileChecker<FileCheck> fileStringChecker;


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
        if (!(codeFile instanceof CodeFileTree)) {
            return result;
        }
        CodeTree codeFileTree = ((CodeFileTree) codeFile).getCodeTree();
        for (Project baselineProject : baselineProjects) {
            for (CodeFile baselineCodeFile : baselineProject.getCodeFileList()) {
                if (Objects.equals(codeFile.getLanguage(), baselineCodeFile.getLanguage()) &&
                        baselineCodeFile instanceof CodeFileTree) {
                    CodeTree baselineCodeFileTree = ((CodeFileTree) baselineCodeFile).getCodeTree();
                    int similarity = treeSimilarityCalculator.calculateTreeSimilarity(codeFileTree, baselineCodeFileTree);
                    result.add(new TreeSimilarity(baselineProject.getName(), baselineCodeFile.getSourceFile(), similarity));
                }
            }
        }
        return result;
    }
}
