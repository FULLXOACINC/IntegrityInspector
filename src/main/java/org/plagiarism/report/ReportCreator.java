package org.plagiarism.report;

import org.plagiarism.cheker.PlagiarismLineChecker;
import org.plagiarism.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportCreator {
    private static final PlagiarismLineChecker LINE_CHECKER = new PlagiarismLineChecker();


    public Report create(Project checkProject, List<Project> baselineProjects) {
        Report report = new Report(new ArrayList<>(),new ArrayList<>());
        for (CodeFile codeFile : checkProject.getCodeFileList()) {
            List<CheckLine> checkedLines = new ArrayList<>();
            for (Line checkedLine : codeFile.getCode()) {
                List<Check> list = new ArrayList<>();
                for (Project baselineProject : baselineProjects) {
                    for (CodeFile baselineCodeFile : baselineProject.getCodeFileList()) {
                        list.addAll(LINE_CHECKER.check(checkedLine, baselineCodeFile, baselineProject.getName(), baselineCodeFile.getSourceFile()));
                    }
                }
                List<LineInfo> similarLines = extractSimilarLines(list);
                if (!similarLines.isEmpty()) {
                    checkedLines.add(new CheckLine(checkedLine, similarLines));
                }
            }
            if (!checkedLines.isEmpty()) {
                report.getProjectCheckList().add(new ProjectCheck(codeFile.getSourceFile(), checkedLines));
            }
        }
        return report;
    }

    private List<LineInfo> extractSimilarLines(List<Check> list) {
        return list
                .stream()
                .sorted(Check.CHECK_COMPARATOR)
                .limit(4)
                .map(x -> new LineInfo(x.getBaselineProject(), x.getBaselineCodeFile(), x.getLevenshteinDistance(), x.getCosineDistance(), x.getJaccardDistance(), x.getBaseline()))
                .collect(Collectors.toList());
    }
}
