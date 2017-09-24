package at.favre.tools.dice;

import at.favre.tools.dice.ui.Arg;
import org.junit.Test;

public class RndToolTest {
    @Test
    public void execute() throws Exception {
        RndTool.execute(Arg.create("java", null, 12, 10, true, false, false, false, false, false, null));
        RndTool.execute(Arg.create("base36", null, 4, null, true, false, false, false, false, false, null));
        RndTool.execute(Arg.create("base64", "verybaadseed", 87, null, true, false, false, false, false, false, null));
        RndTool.execute(Arg.create("base85", null, 15, null, true, true, false, false, false, false, null));
        RndTool.execute(Arg.create("base32", null, 7, null, true, false, false, true, false, false, null));
        RndTool.execute(Arg.create("hex", null, 10, null, true, false, false, true, false, true, null));
    }

}