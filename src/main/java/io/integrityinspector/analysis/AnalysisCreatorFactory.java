package io.integrityinspector.analysis;

import io.integrityinspector.checker.*;
import io.integrityinspector.config.AnalysisConfig;
import io.integrityinspector.config.AppConfig;
import io.integrityinspector.model.Project;
import io.integrityinspector.model.filecheker.FileCheck;
import io.integrityinspector.model.filecheker.FileTreeCheck;

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

    private StringAnalysisCreator createStringAnalysisCreator(AppConfig config) {
        AnalysisConfig analysisConfig = config.getAnalysisConfig();
        PlagiarismLineChecker plagiarismLineChecker = new PlagiarismLineChecker(analysisConfig.getMinLineLength(), analysisConfig.getMaxLineLengthDiff(), analysisConfig.getLevenshteinSimilarityPercent());
        FileChecker<FileCheck> fileStringChecker = new FileStringChecker(plagiarismLineChecker, analysisConfig.getLineSimilarLimit());
        ProjectAnalyzer<FileCheck> projectAnalyzer = getFileCheckProjectAnalyzer(fileStringChecker, analysisConfig);
        CountsExtractor countsExtractor = new CountsExtractorImpl(analysisConfig.getProjectLimit());
        BaseLineProjectLimiter baseLineProjectLimiter = new BaseLineProjectLimiterImpl();
        ProjectChecker<FileCheck, FileChecker<FileCheck>> stringProjectChecker = new ProjectChecker<>(fileStringChecker);
        UniquenessPercentageCalculator uniquenessPercentageCalculator = new UniquenessPercentageCalculatorImpl();
        Zzh1UniquenessCoefficientCalculator zzh1UniquenessCoefficientCalculator = new Zzh1UniquenessCoefficientCalculatorImpl();
        return new StringAnalysisCreator(projectAnalyzer, countsExtractor, baseLineProjectLimiter, stringProjectChecker, uniquenessPercentageCalculator, zzh1UniquenessCoefficientCalculator);
    }

    private ProjectAnalyzer<FileCheck> getFileCheckProjectAnalyzer(FileChecker<FileCheck> fileStringChecker, AnalysisConfig analysisConfig) {
        ProjectChecker<FileCheck, FileChecker<FileCheck>> fullProjectChecker = new ProjectChecker<>(fileStringChecker);
        AnalysisTaskFactory<FileCheck, FileChecker<FileCheck>> analysisTaskFactory = new AnalysisTaskFactory<>(fullProjectChecker);
        ChunkExtractor<Project> chunkExtractor = new ChunkExtractorImpl<>();
        return new MultithreadingProjectAnalyzer<>(analysisConfig.getMultithreadingConfig().getThreadsCount(), chunkExtractor, analysisTaskFactory);
    }

    private TreeAnalysisCreator createTreeAnalysisCreator(AppConfig config) {
        AnalysisConfig analysisConfig = config.getAnalysisConfig();
        PlagiarismLineChecker plagiarismLineChecker = new PlagiarismLineChecker(analysisConfig.getMinLineLength(), analysisConfig.getMaxLineLengthDiff(), analysisConfig.getLevenshteinSimilarityPercent());
        FileChecker<FileCheck> fileStringChecker = new FileStringChecker(plagiarismLineChecker, analysisConfig.getLineSimilarLimit());
        ProjectAnalyzer<FileTreeCheck> projectAnalyzer = getFileTreeCheckProjectAnalyzer(fileStringChecker, analysisConfig);
        CountsExtractor countsExtractor = new CountsExtractorImpl(analysisConfig.getProjectLimit());
        BaseLineProjectLimiter baseLineProjectLimiter = new BaseLineProjectLimiterImpl();
        ProjectChecker<FileCheck, FileChecker<FileCheck>> stringProjectChecker = new ProjectChecker<>(fileStringChecker);
        UniquenessPercentageCalculator uniquenessPercentageCalculator = new UniquenessPercentageCalculatorImpl();

        CodeTreeAnalysisExtractor codeTreeAnalysisExtractor = new CodeTreeAnalysisExtractorImpl(analysisConfig.getProjectLimit());
        Zzh1UniquenessCoefficientCalculator zzh1UniquenessCoefficientCalculator = new Zzh1UniquenessCoefficientCalculatorImpl();
        return new TreeAnalysisCreator(projectAnalyzer, countsExtractor, baseLineProjectLimiter, stringProjectChecker, uniquenessPercentageCalculator, codeTreeAnalysisExtractor, zzh1UniquenessCoefficientCalculator);
    }

    private ProjectAnalyzer<FileTreeCheck> getFileTreeCheckProjectAnalyzer(FileChecker<FileCheck> fileStringChecker, AnalysisConfig analysisConfig) {
        FileChecker<FileTreeCheck> fileTreeChecker = new FileTreeChecker(new TreeSimilarityCalculator(), fileStringChecker);

        ProjectChecker<FileTreeCheck, FileChecker<FileTreeCheck>> fullProjectChecker = new ProjectChecker<>(fileTreeChecker);
        AnalysisTaskFactory<FileTreeCheck, FileChecker<FileTreeCheck>> analysisTaskFactory = new AnalysisTaskFactory<>(fullProjectChecker);
        ChunkExtractor<Project> chunkExtractor = new ChunkExtractorImpl<>();
        return new MultithreadingProjectAnalyzer<>(analysisConfig.getMultithreadingConfig().getThreadsCount(), chunkExtractor, analysisTaskFactory);
    }
}
