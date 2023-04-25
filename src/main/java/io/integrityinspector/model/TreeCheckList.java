package io.integrityinspector.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class TreeCheckList {
    private final String fileName;
    private final List<TreeSimilarity> treeSimilarityList;
}
