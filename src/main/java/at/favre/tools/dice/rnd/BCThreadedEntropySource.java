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

package at.favre.tools.dice.rnd;

import at.favre.lib.crypto.HKDF;

import java.nio.charset.StandardCharsets;

/**
 * Threaded Seed Generator as found in Bouncy Castle implementation
 */
public class BCThreadedEntropySource implements ExpandableEntropySource {
    private final static byte[] SALT = new byte[]{0x6B, 0x0B, (byte) 0xF5, (byte) 0xB4, 0x22, 0x78, 0x03, (byte) 0xD0, 0x39, (byte) 0xDA, 0x2B, 0x17, 0x33, (byte) 0xC7, (byte) 0x8F, 0x33, (byte) 0x81, (byte) 0xB9, (byte) 0x98, 0x7B, (byte) 0xAC, (byte) 0xFD, (byte) 0x97, 0x14, 0x4F, (byte) 0x7F, 0x03, 0x21, 0x5F, (byte) 0xFC, 0x2A, 0x5F};

    private final SeedGenerator threadedSeedGenerator;

    public BCThreadedEntropySource() {
        threadedSeedGenerator = new SeedGenerator();
    }

    @Override
    public byte[] generateEntropy(int lengthByte) {
        return HKDF.fromHmacSha256().extractAndExpand(SALT, threadedSeedGenerator.generateSeed(24, false), this.getClass().getName().getBytes(StandardCharsets.UTF_8), lengthByte);
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
    private final class SeedGenerator implements Runnable {
        private volatile int counter = 0;
        private volatile boolean stop = false;

        public void run() {
            while (!this.stop) {
                this.counter++;
            }

        }

        byte[] generateSeed(int numbytes, boolean fast) {
            Thread t = new Thread(this);
            byte[] result = new byte[numbytes];
            this.counter = 0;
            this.stop = false;
            int last = 0;
            int end;

            t.start();
            if (fast) {
                end = numbytes;
            } else {
                end = numbytes * 8;
            }
            for (int i = 0; i < end; i++) {
                while (this.counter == last) {
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // ignore
                    }
                }
                last = this.counter;
                if (fast) {
                    result[i] = (byte) (last & 0xff);
                } else {
                    int bytepos = i / 8;
                    result[bytepos] = (byte) ((result[bytepos] << 1) | (last & 1));
                }

            }
            stop = true;
            return result;
        }
    }
}
