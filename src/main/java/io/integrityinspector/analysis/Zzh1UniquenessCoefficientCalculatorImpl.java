package io.integrityinspector.analysis;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Zzh1UniquenessCoefficientCalculatorImpl implements Zzh1UniquenessCoefficientCalculator {
    private static final BigDecimal ONE_HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal ONE = BigDecimal.valueOf(1);
    private static final int SCALE = 2;

    @Override
    public BigDecimal calculateZzh1UniquenessCoefficient(BigDecimal totalUniquenessPercentage, int projectLineCount) {
        double projectLineCountNormalized = projectLineCount > 1000 ? 1.0 : ((double) projectLineCount) / 1000;
        double coefficient = 1 / (1 + Math.exp(-Math.PI * projectLineCountNormalized));
        return ONE
                .subtract(ONE_HUNDRED
                        .subtract(totalUniquenessPercentage)
                        .divide(ONE_HUNDRED, SCALE, RoundingMode.HALF_DOWN)
                        .multiply(BigDecimal.valueOf(coefficient)))
                .setScale(SCALE, RoundingMode.HALF_DOWN);
    }
}