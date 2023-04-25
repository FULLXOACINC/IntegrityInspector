package io.integrityinspector.analysis;

import io.integrityinspector.model.TreeCheckList;
import io.integrityinspector.model.filecheker.FileTreeCheck;

import java.util.List;

public interface CodeTreeAnalysisExtractor {
    List<TreeCheckList> codeTreeCheck(List<FileTreeCheck> fileTreeChecks);
}
