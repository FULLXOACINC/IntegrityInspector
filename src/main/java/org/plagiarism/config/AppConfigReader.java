package org.plagiarism.config;

import com.google.gson.Gson;
import org.plagiarism.app.AppParameters;

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

    public AppConfig read(String filePath) throws IOException {
        InputStream inputStream = Files.newInputStream(Paths.get(filePath));
        Reader reader = new InputStreamReader(inputStream);
        return new Gson().fromJson(reader, AppConfig.class);
    }

    public AppConfig readBasedOnParameters(AppParameters parameters) throws IOException {
        if (parameters.getConfigFile() != null) {
            return read(parameters.getConfigFile());
        }
        return read();
    }
}
