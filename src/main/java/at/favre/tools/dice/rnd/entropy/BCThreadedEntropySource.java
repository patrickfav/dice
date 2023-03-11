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

package at.favre.tools.dice.rnd.entropy;

import at.favre.lib.hkdf.HKDF;
import at.favre.tools.dice.rnd.ExpandableEntropySource;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Threaded Seed Generator based on the one found in Bouncy Castle implementation
 */
public class BCThreadedEntropySource implements ExpandableEntropySource {
    private static final byte[] SALT = new byte[]{0x48, (byte) 0xB8, (byte) 0x96, (byte) 0xC6, (byte) 0x87, 0x5C, (byte) 0xD0, (byte) 0xF9, (byte) 0x9D, 0x60,
            (byte) 0xFA, 0x11, 0x3E, 0x68, 0x2A, (byte) 0xD0, 0x48, (byte) 0xE3, (byte) 0x83, (byte) 0x90, (byte) 0xA2, 0x3E, 0x79, (byte) 0xCB, 0x52, (byte) 0xBF,
            (byte) 0xB9, 0x4C, 0x67, 0x1B, (byte) 0xEF, 0x49};

    private final SeedGenerator threadedSeedGenerator;
    private final BlockingQueue<Byte> buffer = new ArrayBlockingQueue<>(64);

    public BCThreadedEntropySource() {
        threadedSeedGenerator = new SeedGenerator();
    }

    @Override
    public byte[] generateEntropy(int lengthByte) {
        return HKDF.fromHmacSha256().extractAndExpand(SALT, threadedSeedGenerator.generateSeed(32), this.getClass().getName().getBytes(StandardCharsets.UTF_8), lengthByte);
    }

    @Override
    public String getInformation() {
        return "Bouncy Castle Threaded Seed Generator";
    }

    /**
     * A thread based seed generator - one source of randomness.
     * <p>
     * Based on an idea from Marcus Lippert.
     * </p>
     *
     * @see <a href="https://github.com/bcgit/bc-java/blob/master/core/src/main/java/org/bouncycastle/crypto/prng/ThreadedSeedGenerator.java">Bouncy Castle Impl</a>
     */
    private final class SeedGenerator {
        private volatile long counter = 0;
        private volatile long last = 0;
        private volatile Thread thread;

        public SeedGenerator() {
            startNewGeneratorThread();
        }

        private final class Counter implements Runnable {
            volatile boolean running = true;

            public void run() {
                while (running) {
                    counter++;
                }
            }

            void cancel() {
                running = false;
            }
        }

        private final class Generator implements Runnable {
            public void run() {
                while (buffer.remainingCapacity() > 0) {
                    Counter counterRunnable = new Counter();
                    new Thread(counterRunnable).start();

                    byte[] result = new byte[8];

                    for (int i = 0; i < 8; i++) {
                        while (counter == last) {
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                // ignore
                            }
                        }
                        last = counter;
                        int bytepos = i / 8;
                        result[bytepos] = (byte) ((result[bytepos] << 1) | (last & 1));
                    }

                    counterRunnable.cancel();
                    //System.out.println("put new " + result[0]);
                    buffer.offer(result[0]);
                }
            }
        }

        byte[] generateSeed(int length) {
            if (buffer.remainingCapacity() < length && (thread == null || thread.getState() == Thread.State.TERMINATED)) {
                startNewGeneratorThread();
            }

            byte[] seed = new byte[length];
            for (int j = 0; j < length; j++) {
                try {
                    seed[j] = buffer.take();
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
            //System.out.println(Hex.encodeHex(seed));
            return seed;
        }

        private void startNewGeneratorThread() {
            thread = new Thread(new Generator());
            thread.setDaemon(true);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        }
    }
}
