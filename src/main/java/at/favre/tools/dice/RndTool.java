package at.favre.tools.dice;

import at.favre.tools.dice.encode.Base32Encoder;
import at.favre.tools.dice.encode.Encoder;
import at.favre.tools.dice.encode.Loader;
import at.favre.tools.dice.service.RandomOrgServiceHandler;
import at.favre.tools.dice.ui.Arg;
import at.favre.tools.dice.ui.CLIParser;
import at.favre.tools.dice.ui.ColumnRenderer;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
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

        SecureRandom secureRandom = new SecureRandom();
        if (arguments.urlencode) {
            System.out.println("Url encode output.");
        }
        if (arguments.seed != null) {
            System.out.println("Use provided seed [" + new Base32Encoder().encode(arguments.seed.getBytes(StandardCharsets.UTF_8)) + "].");
            secureRandom.setSeed(arguments.seed.getBytes(StandardCharsets.UTF_8));
        } else if (!arguments.offline) {
            System.out.print("Fetching from random.org. ");
            RandomOrgServiceHandler.Result seedResult = new RandomOrgServiceHandler(arguments.debug).getRandom();
            if (!seedResult.isError()) {
                secureRandom.setSeed(seedResult.seed);
                System.out.println("Got seed [" + new Base32Encoder().encode(seedResult.seed) + "] after " + seedResult.durationMs + "ms");
            } else {
                System.err.println("ERROR " + seedResult.errorMsg);
                System.err.println("Try using --offline to skip online seeding or --debug for more information.");

                if (arguments.debug && seedResult.t != null) {
                    seedResult.t.printStackTrace();
                }
                System.exit(500);
            }
        }

        System.out.println();

        for (Encoder encoder : encoders) {
            if (Arrays.asList(encoder.names()).contains(arguments.encoding)) {
                printRandoms(arguments, encoder, secureRandom);
                return true;
            }
        }

        return false;
    }

    private static void printRandoms(Arg arguments, Encoder encoder, SecureRandom secureRandom) {
        List<String> outputList = new ArrayList<>(arguments.length);
        for (int i = 0; i < arguments.count; i++) {
            byte[] rnd = new byte[arguments.length];
            secureRandom.nextBytes(rnd);
            String randomEncodedString = encoder.encode(rnd);

            if (arguments.urlencode) {
                try {
                    randomEncodedString = new URLCodec().encode(randomEncodedString);
                } catch (EncoderException e) {
                    throw new IllegalStateException("could not url encode", e);
                }
            }
            outputList.add(randomEncodedString);
        }

        new ColumnRenderer().render(outputList, System.out);
        System.out.println();
    }
}
