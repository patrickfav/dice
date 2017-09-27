package at.favre.tools.dice;

import at.favre.tools.dice.ui.Arg;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class RndToolTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void smokeTestRndTool() throws Exception {
        RndTool.execute(Arg.create("java", null, 12, 10L, true, false, false, false, false, false, null));
        RndTool.execute(Arg.create("base36", null, 4, null, true, false, false, false, false, false, null));
        RndTool.execute(Arg.create("base64", "verybaadseed", 87, null, true, false, false, false, false, false, null));
        RndTool.execute(Arg.create("base85", null, 15, null, true, true, false, false, false, false, null));
        RndTool.execute(Arg.create("base32", null, 7, null, true, false, false, true, false, false, null));
        RndTool.execute(Arg.create("hex", null, 10, null, true, false, false, true, false, true, null));
    }

    @Test
    public void testOutFile() throws Exception {
        File tempFile = testFolder.newFile("out-test.txt");
        assertTrue(tempFile.exists());
        assertTrue(tempFile.isFile());
        assertEquals(0, tempFile.length());

        int length = 100;
        long count = 1024L;
        RndTool.execute(Arg.create("raw", null, length, count, true, false, false, false, false, false, tempFile));

        assertEquals(length * count, tempFile.length());
    }
}