package io.integrityinspector.analysis;

import io.integrityinspector.checker.FileChecker;
import io.integrityinspector.model.Project;
import io.integrityinspector.model.filecheker.FileCheck;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@AllArgsConstructor
public class MultithreadingProjectAnalyzer<T extends FileCheck, K extends FileChecker<T>> implements ProjectAnalyzer<T> {
    private final int nThreads;
    private final ChunkExtractor<Project> chunkExtractor;
    private final AnalysisTaskFactory<T, K> analysisTaskFactory;

    @Override
    public List<T> process(Project checkProject, List<Project> list) {
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Future<List<T>>> futures = new ArrayList<>();
        List<T> result = new ArrayList<>();

        List<List<Project>> chunkedBaseLineProject = chunkExtractor.extractChunks(list, nThreads);

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
