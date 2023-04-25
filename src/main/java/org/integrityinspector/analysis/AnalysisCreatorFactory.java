package org.integrityinspector.analysis;

import org.integrityinspector.checker.*;
import org.integrityinspector.config.AnalysisConfig;
import org.integrityinspector.config.AppConfig;
import org.integrityinspector.model.Project;
import org.integrityinspector.model.filecheker.FileCheck;
import org.integrityinspector.model.filecheker.FileTreeCheck;

public class AnalysisCreatorFactory {

    public AnalysisCreator createAnalysisCreator(AppConfig config) {
        if (config.getParseCodeConfig().getNeedParseTree()) {
            if (config.getAnalysisConfig().getMultithreadingConfig() != null) {
                return createTreeAnalysisCreator(config);
            } else {
                throw new UnsupportedOperationException("Not supported: analysisConfig->multithreadingConfig is null");
            }
        } else {
            return createStringAnalysisCreator(config);
        }
    }

    private static StringAnalysisCreator createStringAnalysisCreator(AppConfig config) {
        AnalysisConfig analysisConfig = config.getAnalysisConfig();
        PlagiarismLineChecker plagiarismLineChecker = new PlagiarismLineChecker(analysisConfig.getMinLineLength(), analysisConfig.getMaxLineLengthDiff(), analysisConfig.getLevenshteinSimilarityPercent());
        FileChecker<FileCheck> fileStringChecker = new FileStringChecker(plagiarismLineChecker, analysisConfig.getLineSimilarLimit());

        ProjectChecker<FileCheck, FileChecker<FileCheck>> fullProjectChecker = new ProjectChecker<>(fileStringChecker);

        AnalysisTaskFactory<FileCheck, FileChecker<FileCheck>> analysisTaskFactory = new AnalysisTaskFactory<>(fullProjectChecker);

        ChunkExtractor<Project> chunkExtractor = new ChunkExtractorImpl<>();

        ProjectAnalyzer<FileCheck> projectAnalyzer = new MultithreadingProjectAnalyzer<>(analysisConfig.getMultithreadingConfig().getThreadsCount(), chunkExtractor, analysisTaskFactory);
        CountsExtractor countsExtractor = new CountsExtractorImpl(analysisConfig.getProjectLimit());
        BaseLineProjectLimiter baseLineProjectLimiter = new BaseLineProjectLimiterImpl();
        ProjectChecker<FileCheck, FileChecker<FileCheck>> stringProjectChecker = new ProjectChecker<>(fileStringChecker);
        UniquenessPercentageCalculator uniquenessPercentageCalculator = new UniquenessPercentageCalculatorImpl();
        return new StringAnalysisCreator(projectAnalyzer, countsExtractor, baseLineProjectLimiter, stringProjectChecker, uniquenessPercentageCalculator);
    }

    private static TreeAnalysisCreator createTreeAnalysisCreator(AppConfig config) {
        AnalysisConfig analysisConfig = config.getAnalysisConfig();
        PlagiarismLineChecker plagiarismLineChecker = new PlagiarismLineChecker(analysisConfig.getMinLineLength(), analysisConfig.getMaxLineLengthDiff(), analysisConfig.getLevenshteinSimilarityPercent());
        FileChecker<FileCheck> fileStringChecker = new FileStringChecker(plagiarismLineChecker, analysisConfig.getLineSimilarLimit());

        FileChecker<FileTreeCheck> fileTreeChecker = new FileTreeChecker(fileStringChecker);
        ProjectChecker<FileTreeCheck, FileChecker<FileTreeCheck>> fullProjectChecker = new ProjectChecker<>(fileTreeChecker);

        AnalysisTaskFactory<FileTreeCheck, FileChecker<FileTreeCheck>> analysisTaskFactory = new AnalysisTaskFactory<>(fullProjectChecker);

        ChunkExtractor<Project> chunkExtractor = new ChunkExtractorImpl<>();

        ProjectAnalyzer<FileTreeCheck> projectAnalyzer = new MultithreadingProjectAnalyzer<>(analysisConfig.getMultithreadingConfig().getThreadsCount(), chunkExtractor, analysisTaskFactory);
        CountsExtractor countsExtractor = new CountsExtractorImpl(analysisConfig.getProjectLimit());
        BaseLineProjectLimiter baseLineProjectLimiter = new BaseLineProjectLimiterImpl();
        ProjectChecker<FileCheck, FileChecker<FileCheck>> stringProjectChecker = new ProjectChecker<>(fileStringChecker);
        UniquenessPercentageCalculator uniquenessPercentageCalculator = new UniquenessPercentageCalculatorImpl();
        CodeTreeAnalysisExtractor codeTreeAnalysisExtractor = new CodeTreeAnalysisExtractorImpl(analysisConfig.getProjectLimit());
        return new TreeAnalysisCreator(projectAnalyzer, countsExtractor, baseLineProjectLimiter, stringProjectChecker, uniquenessPercentageCalculator, codeTreeAnalysisExtractor);
    }
}
