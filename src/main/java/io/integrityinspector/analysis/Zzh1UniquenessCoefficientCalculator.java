package io.integrityinspector.analysis;

import java.math.BigDecimal;

public interface Zzh1UniquenessCoefficientCalculator {
    BigDecimal calculateZzh1UniquenessCoefficient(BigDecimal totalUniquenessPercentage, int projectLineCount);
}
