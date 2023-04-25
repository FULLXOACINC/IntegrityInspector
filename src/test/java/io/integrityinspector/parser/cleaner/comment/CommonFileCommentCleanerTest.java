package io.integrityinspector.parser.cleaner.comment;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommonFileCommentCleanerTest {

    private static final CommentCleaner CLEANER = new CommonFileCommentCleaner();

    @Test
    public void contentWithoutCommentsTest() {
        String code = "This is a not comment";
        String actual = CLEANER.removeComments(code);
        String expected = "This is a not comment";
        assertEquals(expected, actual);
    }

    @Test
    public void oneLineCommentTest() {
        String code = "// This is a comment\nprint('Hello World')\n# Another comment";
        String actual = CLEANER.removeComments(code);
        String expected = "\nprint('Hello World')\n# Another comment";
        assertEquals(expected, actual);
    }

    @Test
    public void oneLineWithHeadContentCommentTest() {
        String code = "test// This is a comment\nprint('Hello World')\n# Another comment";
        String actual = CLEANER.removeComments(code);
        String expected = "test\nprint('Hello World')\n# Another comment";
        assertEquals(expected, actual);
    }

    @Test
    public void multiLineCommentTest() {
        String code = "/* This is a comment\nprint('Hello World')*/\n# Another comment";
        String actual = CLEANER.removeComments(code);
        String expected = "\n# Another comment";
        assertEquals(expected, actual);
    }

    @Test
    public void multiLineWithTailContentCommentTest() {
        String code = "/* This is a comment\nprint('Hello World')*/test\n# Another comment";
        String actual = CLEANER.removeComments(code);
        String expected = "test\n# Another comment";
        assertEquals(expected, actual);
    }

    @Test
    public void multiLineWithHeadContentCommentTest() {
        String code = "test/* This is a comment\nprint('Hello World')*/\n# Another comment";
        String actual = CLEANER.removeComments(code);
        String expected = "test\n# Another comment";
        assertEquals(expected, actual);
    }
}
