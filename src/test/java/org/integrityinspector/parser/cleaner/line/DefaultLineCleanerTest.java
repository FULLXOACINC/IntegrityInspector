package org.integrityinspector.parser.cleaner.line;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DefaultLineCleanerTest {

    private static final LineCleaner CLEANER = new DefaultLineCleaner();

    @Test
    public void oneLineCommentTest() {
        String code = "qwertyuiop[]\';lkjhgfdszxcvbnm,/`1234567890-=<>?\"{}|~!@#$%^&*()_+QWERTYUIOPASDFGHJKLZXCVBNM";
        String actual = CLEANER.cleanLine(code);
        String expected = "qwertyuioplkjhgfdszxcvbnm1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
        assertEquals(expected, actual);
    }
}
