package org.plagiarism.analysis;

import lombok.AllArgsConstructor;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.Project;
import org.plagiarism.model.filecheker.FileFullCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@AllArgsConstructor
public class MultithreadingProjectChecker {
    private final AnalysisConfig config;
    private final int nThreads;


    public List<FileFullCheck> process(Project checkProject, List<Project> list) {
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Future<List<FileFullCheck>>> futures = new ArrayList<>();
        List<FileFullCheck> result = new ArrayList<>();

        List<List<Project>> chunkedBaseLineProject = extractChunkBaseLineProjects(list);

        for (List<Project> chunk : chunkedBaseLineProject) {
            Future<List<FileFullCheck>> future = executorService.submit(new AnalysisTask(config, chunk, checkProject));
            futures.add(future);
        }
        for (Future<List<FileFullCheck>> future : futures) {
            try {
                result.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();
        return result;

    }

    private List<List<Project>> extractChunkBaseLineProjects(List<Project> list) {
        List<List<Project>> chunkedBaseLineProject = new ArrayList<>();
        int chunkSize = list.size() / nThreads;
        for (int i = 0; i < nThreads; i++) {
            int start = i * chunkSize;
            int end = (i + 1) * chunkSize;
            if (i == nThreads - 1) {
                end = list.size();
            }
            List<Project> chunk = list.subList(start, end);
            if (!chunk.isEmpty()) {
                chunkedBaseLineProject.add(chunk);
            }
        }
        return chunkedBaseLineProject;
    }


}
