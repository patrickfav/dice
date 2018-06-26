/*
 *  Copyright 2017 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package at.favre.tools.dice;

import at.favre.lib.bytes.Bytes;
import at.favre.tools.dice.encode.Encoder;
import at.favre.tools.dice.encode.EncoderHandler;
import at.favre.tools.dice.rnd.*;
import at.favre.tools.dice.rnd.entropy.*;
import at.favre.tools.dice.service.ServiceHandler;
import at.favre.tools.dice.service.anuquantum.AnuQuantumServiceHandler;
import at.favre.tools.dice.service.hotbits.HotbitsServiceHandler;
import at.favre.tools.dice.service.randomorg.RandomOrgServiceHandler;
import at.favre.tools.dice.ui.AppException;
import at.favre.tools.dice.ui.Arg;
import at.favre.tools.dice.ui.CLIParser;
import at.favre.tools.dice.ui.ColumnRenderer;
import at.favre.tools.dice.util.MiscUtil;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class RndTool {
    static final int MAX_BYTE_PER_RANDOM = 1024;
    static final long MAX_BYTE_PER_CALL = 10L * 1024L * 1024L * 1024L;

    private RndTool() {
    }

    public static void main(String[] args) {
        Arg arguments = CLIParser.parse(args);

        if (arguments != null) {
            try {
                execute(arguments);
            } catch (AppException e) {
                System.err.println(e.getMessage());
                System.exit(e.errorCode);
            } catch (Exception e) {
                System.exit(1);
            }
        }
    }

    static void execute(Arg arguments) throws AppException {
        long start = System.currentTimeMillis();

        EncoderHandler loader = new EncoderHandler();
        loader.load();

        Encoder encoder = loader.findByName(arguments.encoding());
        EntropyPool entropyPool = new HKDFEntropyPool();
        entropyPool.add(new JDKThreadedEntropySource());
        entropyPool.add(new BCThreadedEntropySource());
        entropyPool.add(new SecureRandomEntropySource());

        if (encoder == null) {
            throw new AppException("Given encoder '" + arguments.encoding() + "' is not available.\n\nAvailable encoders:\n\n" + loader.getFullSupportedEncodingList(), 400);
        }

        if (arguments.length() <= 0 || arguments.length() > MAX_BYTE_PER_RANDOM) {
            throw new AppException("The random length must be between than 1 and " + MAX_BYTE_PER_RANDOM, 401);
        }

        if (arguments.count() != null && arguments.count() <= 0) {
            throw new AppException("Count parameter must be greater than 0", 402);
        }

        if ((arguments.count() != null && (arguments.count() * (long) arguments.length() > MAX_BYTE_PER_CALL))
                || (long) arguments.length() > MAX_BYTE_PER_CALL) {
            throw new AppException("This PRNG can only generate " + MAX_BYTE_PER_CALL + " bytes at once.", 403);
        }

        if (arguments.outFile() != null && arguments.outFile().getParentFile() != null
                && !arguments.outFile().getParentFile().exists()) {
            if (!arguments.outFile().getParentFile().mkdirs()) {
                throw new AppException("could not generate dir structure for " + arguments.outFile(), 404);
            }
        }

        wrapInErrorHandling(arguments, () -> {
            if (arguments.seed() != null) {
                byte[] seed = parseSeed(arguments.seed());
                println("Use provided seed: " + seed.length + " bytes." + getOptionalEntropyWarning(seed), arguments);
                entropyPool.add(new ExternalWeakSeedEntropySource(arguments.seed()));
            }

            if (!arguments.offline()) {
                final List<ServiceHandler<?>> handlers = new ArrayList<>();
                handlers.add(new RandomOrgServiceHandler(arguments.debug()));
                handlers.add(new HotbitsServiceHandler(arguments.debug()));

                if (arguments.enableAnuQuantum()) {
                    handlers.add(new AnuQuantumServiceHandler(arguments.debug()));
                }

                fetch(handlers, arguments, entropyPool, encoder, start);
            } else {
                requestFinished(arguments, encoder, entropyPool, start);
            }
            return true;
        });
    }

    private static byte[] parseSeed(String seed) {
        try {
            return ByteBuffer.allocate(Long.BYTES).putLong(Long.parseLong(seed)).array();
        } catch (Exception e) {
            return seed.getBytes(StandardCharsets.UTF_8);
        }
    }

    private static void wrapInErrorHandling(Arg arguments, Callable r) {
        try {
            r.call();
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
    }

    private static void fetch(final List<ServiceHandler<?>> handlers, final Arg arguments, final EntropyPool entropyPool,
                              final Encoder encoder, final long start) {
        print("Fetch external seed: ", arguments);
        final ExecutorService parallelExecutor = Executors.newFixedThreadPool(4);
        Observable.fromIterable(handlers)
                .flatMapSingle(handler -> handler.asObservable().subscribeOn(Schedulers.from(parallelExecutor)))
                .doFinally(() -> println("", arguments))
                .doFinally(parallelExecutor::shutdown)
                .blockingSubscribe(result -> updateSeed(result, arguments, entropyPool), t -> {
                    System.err.println(System.lineSeparator() + t.getMessage());
                    System.exit(500);
                }, () -> wrapInErrorHandling(arguments, () -> requestFinished(arguments, encoder, entropyPool, start)));
    }

    private static boolean requestFinished(Arg arguments, Encoder encoder, EntropyPool entropyPool, long start) throws Exception {
        println("", arguments);

        ExpandableEntropySource nonceSource = new NonceEntropySource();
        ExpandableEntropySource persoSource = new PersonalizationSource();

        if (arguments.debug()) {
            println(nonceSource.getInformation(), arguments);
            println(persoSource.getInformation(), arguments);
            println(entropyPool.getInformation(), arguments);
        }

        if (arguments.outFile() != null) {
            print("Writing data to " + arguments.outFile(), arguments);
        }

        printRandoms(arguments, encoder, new HmacDrbg(
                new DrbgParameter(
                        MacFactory.Default.hmacSha512(),
                        entropyPool,
                        nonceSource,
                        persoSource.generateEntropy(16))), start);
        return true;
    }

    private static ServiceHandler.Result<?> updateSeed(ServiceHandler.Result<?> result, Arg arguments, EntropyPool entropyPool) throws IOException {
        if (!result.isError()) {
            entropyPool.add(new ExternalStrongSeedEntropySource(result.seed));
            print(result.serviceName + " [" + result.durationMs + "ms] " + getOptionalEntropyWarning(result.seed), arguments);
            return result;
        } else {
            throw new IOException(result.serviceName + ": " + result.errorMsg + System.lineSeparator() + "Try using --offline to skip online seeding or --debug for more information.", result.throwable);
        }
    }

    private static void print(String msg, Arg arg) {
        if (!arg.robot()) {
            arg.cmdLinePrintStream().print(msg);
        }
    }

    private static void println(String msg, Arg arg) {
        if (!arg.robot()) {
            arg.cmdLinePrintStream().println(msg);
        }
    }

    private static String getOptionalEntropyWarning(byte[] seed) {
        StringBuilder sb = new StringBuilder();
        double entropy = Bytes.wrap(seed).entropy();
        if (entropy < 3) {
            sb.append(" (WARN: low entropy of ").append(String.format(Locale.US, "%.2f", Bytes.wrap(seed).entropy())).append(")");
        }
        return sb.toString();
    }

    private static void printRandoms(Arg arguments, Encoder encoder, DeterministicRandomBitGenerator drbg, long startTime) throws IOException {
        boolean useAutoColumn = false;
        if (arguments.count() == null) {
            arguments = arguments.toBuilder().count(Arg.DEFAULT_COUNT).build();
            useAutoColumn = true;
        }

        PrintStream printStream = getStream(arguments);

        try {
            long startRndGen = System.currentTimeMillis();
            long actualCount;

            if (arguments.robot()) {
                actualCount = new ColumnRenderer(encoder.getEncoderFormat(), genFromArg(arguments, encoder, drbg)).renderSingleColumn(arguments.count(), printStream);
            } else if (useAutoColumn) {
                actualCount = new ColumnRenderer(encoder.getEncoderFormat(), genFromArg(arguments, encoder, drbg)).renderAutoColumn(arguments.count(), printStream, arguments.outFile() != null);
            } else {
                actualCount = new ColumnRenderer(encoder.getEncoderFormat(), genFromArg(arguments, encoder, drbg)).render(arguments.count(), printStream, arguments.outFile() != null);
            }

            println(getSummary(System.currentTimeMillis() - startTime, System.currentTimeMillis() - startRndGen, actualCount * arguments.length()), arguments);
        } finally {
            if (printStream != System.out) {
                printStream.close();
            }
        }
    }

    @NotNull
    private static String getSummary(long durationMs, long durationRndGen, long byteGen) {
        double bandwidth = durationRndGen == 0 || byteGen == 0 ? 0 : Math.round(byteGen / durationRndGen / 10.24) / 100.0;
        return System.lineSeparator() + System.lineSeparator() + "[" + getFriendlyFormattedDate() + "][" + MiscUtil.jarVersion() + "] " + byteGen + " bytes generated in " + durationMs + " ms." + (bandwidth > 0 ? " (" + bandwidth + " MB/s)" : "");
    }

    private static ColumnRenderer.RandomGenerator genFromArg(Arg arguments, Encoder encoder, DeterministicRandomBitGenerator drbg) {
        return new ColumnRenderer.DefaultRandomGenerator(encoder, drbg, arguments.length(), arguments.crc32(), arguments.padding(), arguments.urlencode());
    }

    private static PrintStream getStream(Arg arguments) throws FileNotFoundException {
        return arguments.outFile() != null ? new PrintStream(new FileOutputStream(arguments.outFile(), true)) : arguments.cmdLinePrintStream();
    }

    private static String getFriendlyFormattedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        return sdf.format(new Date());
    }
}