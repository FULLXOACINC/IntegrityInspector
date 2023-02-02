package org.plagiarism.checker;

import lombok.AllArgsConstructor;
import org.plagiarism.checker.PlagiarismLineChecker;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AllArgsConstructor
public class FileChecker {
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final int SCALE = 2;
    private final PlagiarismLineChecker lineChecker;
    private final AnalysisConfig config;

    public FileCheck checkFile(CodeFile codeFile, List<Project> baselineProjects) {
        double plagiarismLineCount = 0.0;
        List<CheckLine> checkedLines = new ArrayList<>();
        for (Line checkedLine : codeFile.getCode()) {
            List<Check> checks = compareLineWithBaselineProjects(checkedLine, baselineProjects);
            List<LineInfo> similarLines = extractSimilarLines(checks);
            if (!similarLines.isEmpty()) {
                plagiarismLineCount++;
            }
            checkedLines.add(new CheckLine(checkedLine, similarLines));
        }

        BigDecimal uniquePresent = BigDecimal
                .valueOf(plagiarismLineCount)
                .multiply(ONE_HUNDRED)
                .divide(BigDecimal.valueOf(codeFile.getCode().size()), SCALE, RoundingMode.HALF_DOWN);
        return new FileCheck(codeFile.getSourceFile(), checkedLines, ONE_HUNDRED.subtract(uniquePresent));
    }

    private List<LineInfo> extractSimilarLines(List<Check> list) {
        return list
                .stream()
                .filter(distinctByKey(x -> x.getBaseline().getContent().trim() + x.getBaselineProject()))
                .sorted(Check.CHECK_COMPARATOR)
                .limit(config.getLineSimilarLimit())
                .map(x -> new LineInfo(x.getBaselineProject(), x.getBaselineCodeFile(), x.getLevenshteinDistance(), x.getCosineDistance(), x.getJaccardDistance(), x.getBaseline()))
                .sorted(LineInfo.LINE_INFO_COMPARATOR)
                .collect(Collectors.toList());
    }

    private <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    private List<Check> compareLineWithBaselineProjects(Line checkedLine, List<Project> baselineProjects) {
        List<Check> list = new ArrayList<>();
        for (Project baselineProject : baselineProjects) {
            for (CodeFile baselineCodeFile : baselineProject.getCodeFileList()) {
                list.addAll(lineChecker.check(checkedLine, baselineCodeFile, baselineProject.getName(), baselineCodeFile.getSourceFile()));
            }
        }
        return list;
    }
}
