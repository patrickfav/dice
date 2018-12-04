package at.favre.tools.dice.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class MiscUtilTest {

    @Test
    public void isValidPath() {
        assertTrue(MiscUtil.isValidPath("c:/test"));
        assertTrue(MiscUtil.isValidPath("c:/test/test2"));
        assertTrue(MiscUtil.isValidPath("c:/test/test2/test3"));
        assertTrue(MiscUtil.isValidPath("c:/test/test2.txt"));
        assertFalse(MiscUtil.isValidPath("c:/te:t"));
        assertFalse(MiscUtil.isValidPath("c/te*t"));
        assertTrue(MiscUtil.isValidPath("good.txt"));
        assertFalse(MiscUtil.isValidPath("not|good.txt"));
        assertFalse(MiscUtil.isValidPath("not:good.tx"));
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
