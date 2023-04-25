package org.integrityinspector.analysis;

import lombok.AllArgsConstructor;
import org.integrityinspector.model.CheckLine;
import org.integrityinspector.model.LineInfo;
import org.integrityinspector.model.ProjectCount;
import org.integrityinspector.model.filecheker.FileCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.integrityinspector.model.ProjectCount.PROJECT_COUNT_COMPARATOR;

@AllArgsConstructor
public class CountsExtractorImpl implements CountsExtractor {

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
