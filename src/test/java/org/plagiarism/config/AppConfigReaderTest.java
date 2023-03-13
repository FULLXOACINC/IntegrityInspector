package org.plagiarism.config;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppConfigReaderTest {
    public static final String CONFIG_JSON = "./config_1.json";
    private static AppConfigReader configReader = new AppConfigReader();
    private static AppConfig expected;

    static {
        Set<String> set6 = new HashSet<>();
        Set<String> set7 = new HashSet<>();
        Set<String> set8 = new HashSet<>();

        set6.add("extensions 6 1");
        set6.add("extensions 6 2");
        set6.add("extensions 6 3");

        set7.add("extensions 7 1");
        set7.add("extensions 7 2");

        set8.add("extensions 8 1");

        AdditionalFileExtensionConfig extension6 = new AdditionalFileExtensionConfig(set6, "6");
        AdditionalFileExtensionConfig extension7 = new AdditionalFileExtensionConfig(set7, "7");
        AdditionalFileExtensionConfig extension8 = new AdditionalFileExtensionConfig(set8, "8");

        List<AdditionalFileExtensionConfig> additionalFileExtensions = new ArrayList<>();
        additionalFileExtensions.add(extension6);
        additionalFileExtensions.add(extension7);
        additionalFileExtensions.add(extension8);

        AnalysisConfig analysisConfig = new AnalysisConfig(1, 2, 3, 4, 5.0, 6);
        ParserConfig parseCodeConfig = new ParserConfig(additionalFileExtensions);

        expected = new AppConfig(analysisConfig, parseCodeConfig);
    }

    @Before
    public void before() throws IOException {
        Gson gson = new Gson();
        String str = gson.toJson(expected);
        BufferedWriter writer = new BufferedWriter(new FileWriter("./config_1.json"));
        writer.write(str);
        writer.close();
    }

    @After
    public void after() throws IOException {
        boolean deleteResult = new File("./config_1.json").delete();
        assertTrue(deleteResult);
    }

    @Test
    public void readFromPathTest() throws IOException {
        AppConfig actual = configReader.read(CONFIG_JSON);
        assertEquals(expected, actual);
    }

    @Test
    public void readWithoutResourcesTest() {
        AppConfig actual = configReader.read();
        assertEquals(expected, actual);
    }
}
