package org.plagiarism.parser.cleaner.comment;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PythonFileCommentCleanerTest {

    private static final CommentCleaner CLEANER = new PythonFileCommentCleaner();

    @Test
    public void contentWithoutCommentsTest() {
        String code = "This is a not comment";
        String actual = CLEANER.removeComments(code);
        String expected = "This is a not comment";
        assertEquals(expected, actual);
    }

    @Test
    public void oneLineCommentTest() {
        String code = "# This is a comment\nprint('Hello World')\n# Another comment";
        String actual = CLEANER.removeComments(code);
        String expected = "\nprint('Hello World')\n";
        assertEquals(expected, actual);
    }


    @Test
    public void multiLineCommentTest() {
        String code = "\"\"\" This is a comment\nprint('Hello World')\"\"\"\n Another comment";
        String actual = CLEANER.removeComments(code);
        String expected = "\n Another comment";
        assertEquals(expected, actual);
    }

    @Test
    public void multiLineWithTailContentCommentTest() {
        String code = "\"\"\" This is a comment\nprint('Hello World')\"\"\"test\n";
        String actual = CLEANER.removeComments(code);
        String expected = "test\n";
        assertEquals(expected, actual);
    }

    @Test
    public void multiLineWithHeadContentCommentTest() {
        String code = "test\"\"\" This is a comment\nprint('Hello World')\"\"\"\n";
        String actual = CLEANER.removeComments(code);
        String expected = "test\n";
        assertEquals(expected, actual);
    }
}
