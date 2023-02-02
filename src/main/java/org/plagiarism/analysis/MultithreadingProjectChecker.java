package org.plagiarism.analysis;

import lombok.AllArgsConstructor;
import org.plagiarism.config.AnalysisConfig;
import org.plagiarism.model.FileCheck;
import org.plagiarism.model.Project;

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


    public List<FileCheck> process(Project checkProject, List<Project> list) {
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<Future<List<FileCheck>>> futures = new ArrayList<>();
        List<FileCheck> result = new ArrayList<>();

        List<List<Project>> chunkedBaseLineProject = extractChunkBaseLineProjects(checkProject, list, executorService, futures);

        for (List<Project> chunk : chunkedBaseLineProject) {
            Future<List<FileCheck>> future = executorService.submit(new AnalysisTask(config, chunk, checkProject));
            futures.add(future);
        }
        for (Future<List<FileCheck>> future : futures) {
            try {
                result.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        executorService.shutdown();
        return result;

    }

    private List<List<Project>> extractChunkBaseLineProjects(Project checkProject, List<Project> list, ExecutorService executorService, List<Future<List<FileCheck>>> futures) {
        List<List<Project>> chunkedBaseLineProject = new ArrayList<>();
        int chunkSize = list.size() / nThreads;
        for (int i = 0; i < nThreads; i++) {
            int start = i * chunkSize;
            int end = (i + 1) * chunkSize;
            if (i == nThreads - 1) {
                end = list.size();
            }
            List<Project> chunk = list.subList(start, end);
            chunkedBaseLineProject.add(chunk);
        }
        return chunkedBaseLineProject;
    }


}
