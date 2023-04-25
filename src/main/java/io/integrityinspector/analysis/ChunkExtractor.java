package io.integrityinspector.analysis;

import java.util.List;

public interface ChunkExtractor<T> {
    List<List<T>> extractChunks(List<T> list, int chunkCount);
}
