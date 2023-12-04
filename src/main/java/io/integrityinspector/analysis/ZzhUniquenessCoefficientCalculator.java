package io.integrityinspector.analysis;

import java.math.BigDecimal;

public interface ZzhUniquenessCoefficientCalculator {
    BigDecimal calculateZzhUniquenessCoefficient(BigDecimal totalUniquenessPercentage, int projectLineCount);
}
