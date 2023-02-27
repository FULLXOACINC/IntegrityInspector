package org.plagiarism.model.filecheker;

import lombok.Getter;
import lombok.Setter;
import org.plagiarism.model.TreeSimilarity;

import java.util.List;

@Getter
@Setter
public class FileTreeCheck extends FileCheck {

    private List<TreeSimilarity> codeTreeSimilarityList;

    public FileTreeCheck(String codeFileName, List<TreeSimilarity> codeTreeSimilarityList) {
        super(codeFileName);
        this.codeTreeSimilarityList = codeTreeSimilarityList;
    }
}