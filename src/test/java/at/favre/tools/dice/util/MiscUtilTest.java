package at.favre.tools.dice.util;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class MiscUtilTest {

    @Test
    public void isValidPath() {
        assertTrue(MiscUtil.isValidPath("c:/test"));
        assertTrue(MiscUtil.isValidPath("c:/test/test2"));
        assertTrue(MiscUtil.isValidPath("c:/test/test2/test3"));
        assertTrue(MiscUtil.isValidPath("c:/test/test2.txt"));
        assertTrue(MiscUtil.isValidPath("/test/test2.txt"));
        assertTrue(MiscUtil.isValidPath("/test/test2/"));
        assertTrue(MiscUtil.isValidPath("good.txt"));
    }

    @Test
    public void jarVersion() {
        assertNotNull(MiscUtil.jarVersion());
    }

    @Test
    public void commitHash() {
        String hash = MiscUtil.commitHash();
        assertNotNull(hash);
    }
}
