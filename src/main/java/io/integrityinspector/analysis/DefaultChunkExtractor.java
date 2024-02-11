package io.integrityinspector.analysis;

import java.util.ArrayList;
import java.util.List;

public class DefaultChunkExtractor<T> implements ChunkExtractor<T> {

    @Override
    public List<List<T>> extractChunks(List<T> list, int chunkCount) {
        List<List<T>> chunkedBaseLineProject = new ArrayList<>();
        int chunkSize = list.size() / chunkCount;
        for (int i = 0; i < chunkCount; i++) {
            int start = i * chunkSize;
            int end = (i + 1) * chunkSize;
            if (i == chunkCount - 1) {
                end = list.size();
            }
            List<T> chunk = list.subList(start, end);
            if (!chunk.isEmpty()) {
                chunkedBaseLineProject.add(chunk);
            }
        }
        return chunkedBaseLineProject;
    }
}