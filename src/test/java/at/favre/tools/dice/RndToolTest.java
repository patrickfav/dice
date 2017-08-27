package at.favre.tools.dice;

import at.favre.tools.dice.ui.Arg;
import org.junit.Test;

public class RndToolTest {
    @Test
    public void execute() throws Exception {
        RndTool.execute(new Arg("java", null, 12, 10, true, false, false, false));
    }

}