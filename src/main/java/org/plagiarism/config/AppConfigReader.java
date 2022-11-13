package org.plagiarism.config;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class AppConfigReader {
    private static final String DEFAULT_CONFIG_FILE = "/config.json";


    public AppConfig read() {
        InputStream inputStream = Objects.requireNonNull(this.getClass().getResourceAsStream(DEFAULT_CONFIG_FILE));
        Reader reader = new InputStreamReader(inputStream);
        return new Gson().fromJson(reader, AppConfig.class);
    }

    public AppConfig read(String file) throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get(file));
        Reader reader = new InputStreamReader(inputStream);
        return new Gson().fromJson(reader, AppConfig.class);
    }
}
