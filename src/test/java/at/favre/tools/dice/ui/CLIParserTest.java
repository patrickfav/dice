package at.favre.tools.dice.ui;

import org.apache.tools.ant.types.Commandline;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CLIParserTest {
    @Test
    public void testSimple() throws Exception {
        Arg parsedArg = CLIParser.parse(asArgArray("-l 4 -" + CLIParser.ARG_COUNT + " 1123 " + "-" + CLIParser.ARG_FORMAT + " testTest"));
        Arg expectedArg = new Arg("testTest", null, 4, 1123, false);
        assertEquals(expectedArg, parsedArg);
    }

    @Test
    public void testHelp() throws Exception {
        Arg parsedArg = CLIParser.parse(asArgArray("--help"));
        assertNull(parsedArg);
    }

    @Test
    public void testVersion() throws Exception {
        Arg parsedArg = CLIParser.parse(asArgArray("--version"));
        assertNull(parsedArg);
    }

    public static String[] asArgArray(String cmd) {
        return Commandline.translateCommandline(cmd);
    }
}