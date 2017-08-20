package at.favre.tools.dice.util;

public class CmdUtil {

    public static String jarVersion() {
        return CmdUtil.class.getPackage().getImplementationVersion();
    }
}
