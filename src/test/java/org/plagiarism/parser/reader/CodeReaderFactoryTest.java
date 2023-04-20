package org.plagiarism.parser.reader;

import org.junit.Test;
import org.plagiarism.config.AdditionalFileExtensionConfig;
import org.plagiarism.parser.reader.file.CodeReader;
import org.plagiarism.parser.reader.file.CodeReaderFactory;
import org.plagiarism.parser.reader.file.DefaultReader;
import org.plagiarism.parser.reader.file.PythonReader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CodeReaderFactoryTest {
    private CodeReaderFactory factory;
    private CodeReader expected = new PythonReader(false);

    {
        Set<String> set6 = new HashSet<>();

        set6.add("py_test");
        AdditionalFileExtensionConfig extension6 = new AdditionalFileExtensionConfig(set6, "py");
        factory = new CodeReaderFactory(Arrays.asList(extension6), false);
    }

    @Test
    public void findCodeReaderPositiveTest() {
        CodeReader actual = factory.findCodeReader("py");
        assertEquals(expected, actual);
    }

    @Test
    public void findCodeReaderFromAdditionalFileExtensionConfigTest() {
        CodeReader actual = factory.findCodeReader("py_test");
        assertEquals(expected, actual);
    }

    @Test
    public void findCodeReaderNoFoundTest() {
        CodeReader actual = factory.findCodeReader("test");
        assertEquals(new DefaultReader(), actual);
    }
}
