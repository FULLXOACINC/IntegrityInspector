package io.integrityinspector.analysis;

import io.integrityinspector.model.filecheker.FileCheck;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class UniquenessPercentageCalculatorImpl implements UniquenessPercentageCalculator {
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal ZERO = BigDecimal.valueOf(0).setScale(2, RoundingMode.HALF_DOWN);
    private static final int SCALE = 2;


    public BigDecimal calculateTotalUniquenessPercentage(List<FileCheck> filteredFileStringChecks) {
        long checkedLineCount = filteredFileStringChecks
                .stream()
                .map(x -> x.getCheckedLines().size())
                .reduce(Integer::sum).orElse(0);
        long matchedLineCount = filteredFileStringChecks
                .stream()
                .flatMap(x -> x.getCheckedLines().stream())
                .filter(x -> x.getSimilarLines().isEmpty())
                .count();
        if (checkedLineCount == 0 || matchedLineCount == 0) {
            return ZERO;
        }
        return BigDecimal
                .valueOf(matchedLineCount)
                .multiply(ONE_HUNDRED)
                .divide(BigDecimal.valueOf(checkedLineCount), SCALE, RoundingMode.HALF_DOWN);
    }
}