package at.favre.tools.dice.util;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Properties;

public final class MiscUtil {
    /**
     * <pre>
     * Checks if a string is a valid path.
     * Null safe.
     *
     * Calling examples:
     *    isValidPath("c:/test");      //returns true
     *    isValidPath("c:/te:t");      //returns false
     *    isValidPath("c:/te?t");      //returns false
     *    isValidPath("c/te*t");       //returns false
     *    isValidPath("good.txt");     //returns true
     *    isValidPath("not|good.txt"); //returns false
     *    isValidPath("not:good.txt"); //returns false
     * </pre>
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException | NullPointerException ex) {
            return false;
        }

        return true;
    }


    public static String jarVersion() {
        String version = MiscUtil.class.getPackage().getImplementationVersion();
        if (version == null) {
            try {
                Properties properties = new Properties();
                properties.load(MiscUtil.class.getClassLoader().getResourceAsStream("git.properties"));
                version = properties.get("git.commit.id").toString().substring(0, 7);
            } catch (Exception e) {
                version = "debug";
            }

        }
        return version;
    }
}
