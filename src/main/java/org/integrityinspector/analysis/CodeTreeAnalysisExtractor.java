package org.integrityinspector.analysis;

import org.integrityinspector.model.TreeCheckList;
import org.integrityinspector.model.filecheker.FileTreeCheck;

import java.util.List;

public interface CodeTreeAnalysisExtractor {
    List<TreeCheckList> codeTreeCheck(List<FileTreeCheck> fileTreeChecks);
}
