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

import at.favre.lib.crypto.HKDF;
import at.favre.tools.dice.rnd.ExpandableEntropySource;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Threaded Seed Generator based on the one found in Bouncy Castle implementation
 */
public class BCThreadedEntropySource implements ExpandableEntropySource {
    private final static byte[] SALT = new byte[]{0x6B, 0x0B, (byte) 0xF5, (byte) 0xB4, 0x22, 0x78, 0x03, (byte) 0xD0, 0x39, (byte) 0xDA, 0x2B, 0x17, 0x33, (byte) 0xC7, (byte) 0x8F, 0x33, (byte) 0x81, (byte) 0xB9, (byte) 0x98, 0x7B, (byte) 0xAC, (byte) 0xFD, (byte) 0x97, 0x14, 0x4F, (byte) 0x7F, 0x03, 0x21, 0x5F, (byte) 0xFC, 0x2A, 0x5F};

    private final SeedGenerator threadedSeedGenerator;
    private final BlockingQueue<Byte> buffer = new ArrayBlockingQueue<>(128);

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

        public SeedGenerator() {
            new Thread(new Generator()).start();
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
                while (true) {
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

                    try {
                        buffer.put(result[0]);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }

        byte[] generateSeed(int length) {
            byte[] seed = new byte[length];
            for (int j = 0; j < length; j++) {
                try {
                    seed[j] = buffer.take();
                } catch (InterruptedException e) {
                    throw new IllegalStateException(e);
                }
            }
            System.out.println(Hex.encodeHex(seed));
            return seed;
        }
    }
}
