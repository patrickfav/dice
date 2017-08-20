package at.favre.tools.dice;

import at.favre.tools.dice.encode.Base32Encoder;
import at.favre.tools.dice.encode.Encoder;
import at.favre.tools.dice.encode.EncoderHandler;
import at.favre.tools.dice.service.RandomOrgServiceHandler;
import at.favre.tools.dice.ui.Arg;
import at.favre.tools.dice.ui.CLIParser;
import at.favre.tools.dice.ui.ColumnRenderer;
import at.favre.tools.dice.util.ByteUtils;
import at.favre.tools.dice.util.Entropy;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        EncoderHandler loader = new EncoderHandler();
        loader.load();

        Encoder encoder = loader.findByName(arguments.encoding);

        if (encoder == null) {
            System.err.println("Given encoder '" + arguments.encoding + "' is not available.");
            System.err.println("\nAvailable encoders:\n\n" + loader.returnRegistryInfo());
            System.exit(2);
        }

        SecureRandom secureRandom = new SecureRandom();
        if (arguments.urlencode) {
            System.out.println("Url encode output.");
        }
        if (arguments.seed != null) {
            System.out.println("Use provided seed " + printWithEntropy(arguments.seed.getBytes(StandardCharsets.UTF_8)) + ".");
            secureRandom.setSeed(arguments.seed.getBytes(StandardCharsets.UTF_8));
        } else if (!arguments.offline) {
            System.out.print("Fetching from random.org. ");
            RandomOrgServiceHandler.Result seedResult = new RandomOrgServiceHandler(arguments.debug).getRandom();
            if (!seedResult.isError()) {
                secureRandom.setSeed(seedResult.seed);
                System.out.println("Got seed " + printWithEntropy(seedResult.seed) + " after " + seedResult.durationMs + "ms");
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

        printRandoms(arguments, encoder, secureRandom);
        return true;
    }

    private static String printWithEntropy(byte[] seed) {
        StringBuilder sb = new StringBuilder();
        sb.append(new Base32Encoder().encode(seed));
        double entropy = new Entropy<>(ByteUtils.toList(seed)).entropy();
        if (entropy < 3) {
            sb.append(" (WARN: low entropy of ").append(String.format(Locale.US, "%.2f", new Entropy<>(ByteUtils.toList(seed)).entropy())).append(")");
        }
        return sb.toString();
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

    public static String jarVersion() {
        String version = RndTool.class.getPackage().getImplementationVersion();
        if (version == null) {
            version = "debug";
        }
        return version;
    }
}
