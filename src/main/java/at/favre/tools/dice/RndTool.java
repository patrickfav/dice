package at.favre.tools.dice;

import at.favre.tools.dice.encode.Encoder;
import at.favre.tools.dice.encode.EncoderHandler;
import at.favre.tools.dice.rnd.*;
import at.favre.tools.dice.service.RandomOrgServiceHandler;
import at.favre.tools.dice.ui.Arg;
import at.favre.tools.dice.ui.CLIParser;
import at.favre.tools.dice.ui.ColumnRenderer;
import at.favre.tools.dice.util.ByteUtils;
import at.favre.tools.dice.util.Entropy;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RndTool {

    private static final int MAX_RND_LENGTH = 640;
    private static final int MAX_COUNT = 1024;
    private static final byte[] RND_TOOL_PERSONALIZATION = new byte[]{(byte) 0xE9, 0x36, (byte) 0x9C, 0x6B, (byte) 0xC4, 0x29, 0x53, 0x3D, (byte) 0xCA, 0x46, 0x24, 0x03, 0x72, 0x5C, 0x5F, (byte) 0xCF, (byte) 0xB8, 0x1C, (byte) 0xF2, 0x36};

    public static void main(String[] args) {
        Arg arguments = CLIParser.parse(args);

        if (arguments != null) {
            if (!execute(arguments)) {
                System.exit(1);
            }
        }
    }

    static boolean execute(Arg arguments) {
        long start = System.currentTimeMillis();

        EncoderHandler loader = new EncoderHandler();
        loader.load();

        Encoder encoder = loader.findByName(arguments.encoding());
        EntropyPool entropyPool = new HKDFEntropyPool();
        entropyPool.add(new SecureRandomEntropySource());

        if (encoder == null) {
            System.err.println("Given encoder '" + arguments.encoding() + "' is not available.");
            System.err.println("\nAvailable encoders:\n\n" + loader.getFullSupportedEncodingList());
            System.exit(400);
        }

        if (arguments.length() > MAX_RND_LENGTH || arguments.length() <= 0) {
            System.err.println("The random length must be between 1 and " + MAX_RND_LENGTH + " byte");
            System.exit(401);
        }

        if (arguments.count() != null && (arguments.count() > MAX_COUNT || arguments.count() <= 0)) {
            System.err.println("Count parameter must be between 1 and " + MAX_COUNT);
            System.exit(402);
        }

        try {

            /*if (arguments.debug()) {
                println("Used secureRandom class is " + secureRandom.getProvider().getInfo() + " (" + secureRandom.getProvider().getName() + "/v" + secureRandom.getProvider().getVersion() + ")", arguments);
            }*/

            if (arguments.urlencode()) {
                println("Url encode output.", arguments);
            }

            if (arguments.seed() != null) {
                println("Use provided seed " + printWithEntropy(arguments.seed().getBytes(StandardCharsets.UTF_8)) + ".", arguments);
                entropyPool.add(new ExternalWeakSeedEntropySource(arguments.seed()));
            }

            if (!arguments.offline()) {
                print("Fetching from random.org. ", arguments);
                RandomOrgServiceHandler.Result seedResult = new RandomOrgServiceHandler(arguments.debug()).getRandom();
                if (!seedResult.isError()) {
                    entropyPool.add(new ExternalStrongSeedEntropySource(seedResult.seed));
                    println("Got seed " + printWithEntropy(seedResult.seed) + " after " + seedResult.durationMs + "ms", arguments);
                } else {
                    System.err.println(seedResult.errorMsg);
                    System.err.println("Try using --offline to skip online seeding or --debug for more information.");

                    if (arguments.debug() && seedResult.throwable != null) {
                        seedResult.throwable.printStackTrace();
                    }
                    System.exit(500);
                }
            }
            println("", arguments);
            printRandoms(arguments, encoder, new HmacDrbg(
                    (ExpandableEntropySource) entropyPool,
                    new NonceEntropySource(),
                    new PersonalizationSource()), start);

        } catch (Exception e) {
            System.err.print("Could not create random bits.");

            if (e.getMessage() != null) {
                System.err.print(" " + e.getMessage());
            }

            System.err.println();

            if (arguments.debug()) {
                System.err.println();
                e.printStackTrace();
            }

            System.exit(501);
        }

        return true;
    }

    private static void print(String msg, Arg arg) {
        if (!arg.robot()) {
            System.out.print(msg);
        }
    }

    private static void println(String msg, Arg arg) {
        if (!arg.robot()) {
            System.out.println(msg);
        }
    }

    private static String printWithEntropy(byte[] seed) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(seed.length).append(" byte]");
        double entropy = new Entropy<>(ByteUtils.toList(seed)).entropy();
        if (entropy < 3) {
            sb.append(" (WARN: low entropy of ").append(String.format(Locale.US, "%.2f", new Entropy<>(ByteUtils.toList(seed)).entropy())).append(")");
        }
        return sb.toString();
    }

    private static void printRandoms(Arg arguments, Encoder encoder, DeterministicRandomBitGenerator drbg, long startTime) {
        List<String> outputList = new ArrayList<>(arguments.length());

        boolean useAutoColumn = false;
        if (arguments.count() == null) {
            arguments = arguments.toBuilder().count(Arg.DEFAULT_COUNT).build();
            useAutoColumn = true;
        }

        int countGenerated = arguments.count() + (useAutoColumn ? 20 : 0);
        for (int i = 0; i < countGenerated; i++) {
            byte[] rnd = drbg.nextBytes(arguments.length());

            if (arguments.crc32()) {
                rnd = ByteUtils.appendCrc32(rnd);
            }

            String randomEncodedString = arguments.padding() ? encoder.encodePadded(rnd) : encoder.encode(rnd);

            if (arguments.urlencode()) {
                try {
                    randomEncodedString = new URLCodec().encode(randomEncodedString);
                } catch (EncoderException e) {
                    throw new IllegalStateException("could not url encode", e);
                }
            }
            outputList.add(randomEncodedString);
        }

        int actualCount;
        if (arguments.robot()) {
            actualCount = new ColumnRenderer().renderSingleColumn(outputList, System.out);
        } else if (useAutoColumn) {
            actualCount = new ColumnRenderer().renderAutoColumn(arguments.count(), outputList, System.out);
        } else {
            actualCount = new ColumnRenderer().render(outputList, System.out);
        }

        print("\n\n[" + new Date().toString() + "][" + jarVersion() + "] " + actualCount * arguments.length() + " bytes generated in " + (System.currentTimeMillis() - startTime) + " ms.", arguments);
    }

    public static String jarVersion() {
        String version = RndTool.class.getPackage().getImplementationVersion();
        if (version == null) {
            version = "debug";
        }
        return version;
    }
}
