package io.integrityinspector.analysis;

import io.integrityinspector.model.filecheker.FileCheck;

import java.math.BigDecimal;
import java.util.List;

public interface UniquenessPercentageCalculator {
    BigDecimal calculateTotalUniquenessPercentage(List<FileCheck> filteredFileStringChecks);
}
