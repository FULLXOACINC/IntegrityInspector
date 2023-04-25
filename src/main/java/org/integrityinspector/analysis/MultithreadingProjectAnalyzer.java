package org.integrityinspector.analysis;

import lombok.AllArgsConstructor;
import org.integrityinspector.checker.FileChecker;
import org.integrityinspector.model.Project;
import org.integrityinspector.model.filecheker.FileCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@AllArgsConstructor
public class MultithreadingProjectAnalyzer<T extends FileCheck, K extends FileChecker<T>> implements ProjectAnalyzer<T> {
    private final int nThreads;
    private final ChunkExtractor<Project> chunkExtractorImpl;
    private final AnalysisTaskFactory<T, K> analysisTaskFactory;

    @Override
    public List<T> process(Project checkProject, List<Project> list) {
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Future<List<T>>> futures = new ArrayList<>();
        List<T> result = new ArrayList<>();

        List<List<Project>> chunkedBaseLineProject = chunkExtractorImpl.extractChunks(list, nThreads);

        for (List<Project> chunk : chunkedBaseLineProject) {

            Future<List<T>> future = executorService.submit(analysisTaskFactory.createTask(chunk, checkProject));
            futures.add(future);
        }
        for (Future<List<T>> future : futures) {
            try {
                result.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();
        return result;

    }


}
