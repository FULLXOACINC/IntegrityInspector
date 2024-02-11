package io.integrityinspector.analysis;

import io.integrityinspector.model.CheckLine;
import io.integrityinspector.model.LineInfo;
import io.integrityinspector.model.ProjectCount;
import io.integrityinspector.model.filecheker.FileCheck;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.integrityinspector.model.ProjectCount.PROJECT_COUNT_COMPARATOR;

@AllArgsConstructor
public class DefaultCountsExtractor implements CountsExtractor {

    private int limit;

    @Override
    public <T extends FileCheck> List<ProjectCount> extractCountsPerProject(List<T> fileChecks) {
        Map<String, Integer> map = extractMapWithCounts(fileChecks);
        List<ProjectCount> result = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            result.add(new ProjectCount(entry.getKey(), entry.getValue()));
        }
        return result
                .stream()
                .sorted(PROJECT_COUNT_COMPARATOR)
                .limit(this.limit)
                .collect(Collectors.toList());
    }

    private <T extends FileCheck> Map<String, Integer> extractMapWithCounts(List<T> fileChecks) {
        Map<String, Integer> map = new HashMap<>();
        for (FileCheck file : fileChecks) {
            for (CheckLine checkLine : file.getCheckedLines()) {
                for (LineInfo similarLine : checkLine.getSimilarLines()) {
                    if (!map.containsKey(similarLine.getProject())) {
                        map.put(similarLine.getProject(), 1);
                    } else {
                        map.put(similarLine.getProject(), map.get(similarLine.getProject()) + 1);
                    }
                }
            }
        }
        return map;
    }


}
