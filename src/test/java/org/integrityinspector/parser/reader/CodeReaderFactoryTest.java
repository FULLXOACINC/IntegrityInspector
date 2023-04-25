package org.integrityinspector.parser.reader;

import org.integrityinspector.config.AdditionalFileExtensionConfig;
import org.integrityinspector.parser.reader.file.CodeReader;
import org.integrityinspector.parser.reader.file.CodeReaderFactory;
import org.integrityinspector.parser.reader.file.DefaultReader;
import org.integrityinspector.parser.reader.file.PythonReader;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class CodeReaderFactoryTest {
    private final CodeReaderFactory factory;
    private final CodeReader expected = new PythonReader(false);

    {
        Set<String> set6 = new HashSet<>();

        set6.add("py_test");
        AdditionalFileExtensionConfig extension6 = new AdditionalFileExtensionConfig(set6, "py");
        factory = new CodeReaderFactory(Collections.singletonList(extension6), false);
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
