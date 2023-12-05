package io.integrityinspector.analysis;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ZzhUniquenessCoefficientCalculatorImpl implements ZzhUniquenessCoefficientCalculator {
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final int SCALE = 2;

    @Override
    public BigDecimal calculateZzhUniquenessCoefficient(BigDecimal totalUniquenessPercentage, int projectLineCount) {
        double projectLineCountDouble = projectLineCount > 1000 ? 1.0 : ((double) projectLineCount) / 1000;
        double coefficient = 1 / (1 + Math.exp(-Math.PI * projectLineCountDouble));
        return ONE_HUNDRED
                .subtract(ONE_HUNDRED
                        .subtract(totalUniquenessPercentage)
                        .divide(ONE_HUNDRED, SCALE, RoundingMode.HALF_DOWN)
                        .multiply(BigDecimal.valueOf(coefficient))
                        .multiply(ONE_HUNDRED))
                .setScale(SCALE, RoundingMode.HALF_DOWN);
    }
}