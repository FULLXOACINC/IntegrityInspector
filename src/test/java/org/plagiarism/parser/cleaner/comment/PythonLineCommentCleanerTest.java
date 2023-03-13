package org.plagiarism.parser.cleaner.comment;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PythonLineCommentCleanerTest {

    private static final CommentCleaner CLEANER = new PythonLineCommentCleaner();

    @Test
    public void contentWithoutCommentsTest() {
        String code = "This is a not comment";
        String actual = CLEANER.removeComments(code);
        String expected = "This is a not comment";
        assertEquals(expected, actual);
    }

    @Test
    public void oneLineCommentTest() {
        String code = "# This is a comment";
        String actual = CLEANER.removeComments(code);
        String expected = "";
        assertEquals(expected, actual);
    }

    @Test
    public void oneLineCommentWithHeadContentTest() {
        String code = "test# This is a comment";
        String actual = CLEANER.removeComments(code);
        String expected = "test";
        assertEquals(expected, actual);
    }

}
