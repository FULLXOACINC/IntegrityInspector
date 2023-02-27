package org.plagiarism.checker;

import org.plagiarism.antlr.CodeTree;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.CodeFile;
import org.plagiarism.model.Project;
import org.plagiarism.model.TreeSimilarity;
import org.plagiarism.model.filecheker.FileFullCheck;
import org.plagiarism.model.filecheker.FileStringCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FileFullChecker implements FileChecker<FileFullCheck> {
    private static final TreeSimilarityCalculator TREE_SIMILARITY_CALCULATOR = new TreeSimilarityCalculator();
    private final FileStringChecker fileStringChecker;

    public FileFullChecker(PlagiarismLineChecker lineChecker, AnalysisConfig config) {
        this.fileStringChecker = new FileStringChecker(lineChecker, config);
    }

    public FileFullCheck checkFile(CodeFile codeFile, List<Project> baselineProjects) {
        FileStringCheck fileStringCheck = fileStringChecker.checkFile(codeFile, baselineProjects);
        List<TreeSimilarity> codeTreeSimilarityList = calculateTreeSimilarity(codeFile, baselineProjects);

        return new FileFullCheck(
                codeFile.getSourceFile(),
                fileStringCheck.getCheckedLines(),
                fileStringCheck.getUniqueStringPresent(),
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
