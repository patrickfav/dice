package at.favre.tools.dice;

import at.favre.tools.dice.ui.AppException;
import at.favre.tools.dice.ui.Arg;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static junit.framework.TestCase.*;

public class RndToolTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void smokeTestRndTool() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        RndTool.execute(Arg.create(new PrintStream(baos), "java", null, 12, 10L, true, false, false, false, false, false, null));
        checkOutput(baos);

        RndTool.execute(Arg.create(new PrintStream(baos), "base36", null, 4, null, true, false, false, false, false, false, null));
        checkOutput(baos);

        RndTool.execute(Arg.create(new PrintStream(baos), "base64", "verybaadseed", 87, null, true, false, false, false, false, false, null));
        checkOutput(baos);

        RndTool.execute(Arg.create(new PrintStream(baos), "base85", null, 15, null, true, true, false, false, false, false, null));
        checkOutput(baos);

        RndTool.execute(Arg.create(new PrintStream(baos), "base32", null, 7, null, true, false, false, true, false, false, null));
        checkOutput(baos);

        RndTool.execute(Arg.create(new PrintStream(baos), "hex", null, 10, null, true, false, false, true, false, true, null));
        checkOutput(baos);

    }

    @Test(expected = AppException.class)
    public void zeroLength() throws Exception {
        RndTool.execute(Arg.create(System.out, "hex", null, 0, 10L, true, false, false, false, false, false, null));
    }

    @Test(expected = AppException.class)
    public void zeroLengthWithDebug() throws Exception {
        RndTool.execute(Arg.create(System.out, "hex", null, 0, 10L, true, false, true, false, false, false, null));
    }

    @Test(expected = AppException.class)
    public void minusLength() throws Exception {
        RndTool.execute(Arg.create(System.out, "hex", null, -1, 10L, true, false, false, false, false, false, null));
    }

    @Test(expected = AppException.class, timeout = 500)
    public void tooManyBytes() throws Exception {
        RndTool.execute(Arg.create(System.out, "hex", null, 1, RndTool.MAX_BYTE_PER_CALL + 1, true, false, false, false, false, false, null));
    }

    @Test(timeout = 20 * 1000)
    public void onlineTest() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        RndTool.execute(Arg.create(new PrintStream(baos), "hex", null, 1, 10L, false, false, true, false, false, false, null));
        checkOutput(baos);
    }

    @Test
    public void debugTest() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        RndTool.execute(Arg.create(new PrintStream(baos), "hex", null, 1, 10L, true, false, false, false, false, false, null));
        checkOutput(baos);
    }

    private void checkOutput(ByteArrayOutputStream baos) {
        byte[] output = baos.toByteArray();
        assertFalse(output.length == 0);
        System.out.println(new String(output, StandardCharsets.UTF_8));
        baos.reset();
    }

    @Test
    public void testOutFile() throws Exception {
        File tempFile = testFolder.newFile("out-test.txt");
        assertTrue(tempFile.exists());
        assertTrue(tempFile.isFile());
        assertEquals(0, tempFile.length());

        int length = 100;
        long count = 1024L;
        RndTool.execute(Arg.create(System.out, "raw", null, length, count, true, false, false, false, false, false, tempFile));

        assertEquals(length * count, tempFile.length());
    }
}