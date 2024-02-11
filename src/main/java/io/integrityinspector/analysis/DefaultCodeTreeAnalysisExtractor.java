package io.integrityinspector.analysis;

import io.integrityinspector.model.TreeCheckList;
import io.integrityinspector.model.TreeSimilarity;
import io.integrityinspector.model.filecheker.FileTreeCheck;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.integrityinspector.model.TreeSimilarity.TREE_SCORE_COMPARATOR;

@AllArgsConstructor
public class DefaultCodeTreeAnalysisExtractor implements CodeTreeAnalysisExtractor {

    private int projectLimit;

    @Override
    public List<TreeCheckList> codeTreeCheck(List<FileTreeCheck> fileTreeChecks) {
        return limitTreeCheck(convertToFileTreeCheckList(fileTreeChecks));
    }

    private List<TreeCheckList> limitTreeCheck(List<TreeCheck> checks) {
        List<TreeCheckList> result = new ArrayList<>();
        Map<String, List<TreeCheck>> fileCheckMap = checks
                .stream()
                .collect(Collectors.groupingBy(TreeCheck::getFileName));
        for (Map.Entry<String, List<TreeCheck>> checkForFile : fileCheckMap.entrySet()) {
            List<TreeSimilarity> filteredTreeSimilarityList = checkForFile.getValue()
                    .stream()
                    .map(TreeCheck::getTreeSimilarity)
                    .sorted(TREE_SCORE_COMPARATOR)
                    .filter(x -> x.getSimilarityScore() != Integer.MAX_VALUE)
                    .limit(projectLimit)
                    .collect(Collectors.toList());

            result.add(new TreeCheckList(checkForFile.getKey(), filteredTreeSimilarityList));
        }
        return result;

    }

    private List<TreeCheck> convertToFileTreeCheckList(List<FileTreeCheck> fileTreeChecks) {
        List<TreeCheck> list = new ArrayList<>();
        for (FileTreeCheck fileTreeCheck : fileTreeChecks) {
            for (TreeSimilarity treeSimilarity : fileTreeCheck.getCodeTreeSimilarityList()) {
                list.add(new TreeCheck(fileTreeCheck.getCodeFileName(), treeSimilarity));
            }
        }
        return list;
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    @Getter
    public static class TreeCheck {
        private final String fileName;
        private final TreeSimilarity treeSimilarity;
    }

}
