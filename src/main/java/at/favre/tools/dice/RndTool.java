package at.favre.tools.dice;

import at.favre.tools.dice.encode.Encoder;
import at.favre.tools.dice.encode.Loader;
import at.favre.tools.dice.service.RandomOrgServiceHandler;
import at.favre.tools.dice.ui.Arg;
import at.favre.tools.dice.ui.CLIParser;
import at.favre.tools.dice.util.ByteUtils;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class RndTool {

    public static void main(String[] args) {
        Arg arguments = CLIParser.parse(args);

        if (arguments != null) {
            if (!execute(arguments)) {
                System.exit(1);
            }
        }
    }

    static boolean execute(Arg arguments) {
        Loader loader = new Loader();
        List<Encoder> encoders = loader.load();

        byte[] seed;
        SecureRandom secureRandom = new SecureRandom();
        if (arguments.online) {
            System.out.println("Getting seed from random.org ...");
            seed = new RandomOrgServiceHandler(arguments.debug).getRandom();
            secureRandom.setSeed(seed);
            System.out.println("Using seed "+ ByteUtils.bytesToHex(seed)+"\n");
        }

        for (Encoder encoder : encoders) {
            if (Arrays.asList(encoder.names()).contains(arguments.encoding)) {
                printRandoms(arguments, encoder, secureRandom);
                return true;
            }
        }

        return false;
    }

    private static void printRandoms(Arg arguments, Encoder encoder, SecureRandom secureRandom) {
        for (int i = 0; i < arguments.count; i++) {
            byte[] rnd = new byte[arguments.length];
            secureRandom.nextBytes(rnd);
            if (arguments.seed != null) {
                secureRandom.setSeed(arguments.seed.getBytes(StandardCharsets.UTF_8));
            }
            String encoded = encoder.encode(rnd);
            System.out.println(encoded);
        }
    }
}
