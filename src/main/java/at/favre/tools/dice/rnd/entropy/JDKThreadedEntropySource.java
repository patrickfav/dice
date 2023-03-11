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
import java.security.PrivilegedAction;

/**
 * This is a port of the Thread Seed Generator in SUN's SeedGenerator class.
 * It use some faster timings which may make it less secure but the original
 * implementation is awfully slow (min 250 ms per byte).
 * <p>
 * This is the fallback if neither the personalization nor the secureRandom
 * environment provides good entropy.
 */
public final class JDKThreadedEntropySource implements ExpandableEntropySource {
    private static final byte[] SALT = new byte[]{0x75, (byte) 0xCC, 0x3A, 0x7B, 0x21, 0x6D, 0x08, (byte) 0xAD, (byte) 0xD9, (byte) 0xD7, (byte) 0xA3,
            0x35, 0x1C, 0x56, 0x13, 0x2C, 0x44, 0x2E, 0x74, (byte) 0xB2, 0x14, 0x2C, 0x15, 0x39, 0x5A, (byte) 0xC6, (byte) 0xBA, 0x3C, (byte) 0xCF,
            0x71, 0x19, 0x2B};

    private final ThreadedSeedGenerator threadedSeedGenerator;

    public JDKThreadedEntropySource() {
        threadedSeedGenerator = new ThreadedSeedGenerator();
    }

    @Override
    public byte[] generateEntropy(int lengthByte) {
        byte[] seed = new byte[12];
        threadedSeedGenerator.getSeedBytes(seed);
        return HKDF.fromHmacSha256().extractAndExpand(SALT, seed, this.getClass().getName().getBytes(StandardCharsets.UTF_8), lengthByte);
    }

    @Override
    public String getInformation() {
        return "JDK Threaded Seed Generator";
    }

    private static class ThreadedSeedGenerator implements Runnable {
        // The permutation was calculated by generating 64k of random
        // data and using it to mix the trivial permutation.
        // It should be evenly distributed. The specific values
        // are not crucial to the security of this class.
        private static byte[] rndTab = {
                56, 30, -107, -6, -86, 25, -83, 75, -12, -64,
                5, -128, 78, 21, 16, 32, 70, -81, 37, -51,
                -43, -46, -108, 87, 29, 17, -55, 22, -11, -111,
                -115, 84, -100, 108, -45, -15, -98, 72, -33, -28,
                31, -52, -37, -117, -97, -27, 93, -123, 47, 126,
                -80, -62, -93, -79, 61, -96, -65, -5, -47, -119,
                14, 89, 81, -118, -88, 20, 67, -126, -113, 60,
                -102, 55, 110, 28, 85, 121, 122, -58, 2, 45,
                43, 24, -9, 103, -13, 102, -68, -54, -101, -104,
                19, 13, -39, -26, -103, 62, 77, 51, 44, 111,
                73, 18, -127, -82, 4, -30, 11, -99, -74, 40,
                -89, 42, -76, -77, -94, -35, -69, 35, 120, 76,
                33, -73, -7, 82, -25, -10, 88, 125, -112, 58,
                83, 95, 6, 10, 98, -34, 80, 15, -91, 86,
                -19, 52, -17, 117, 49, -63, 118, -90, 36, -116,
                -40, -71, 97, -53, -109, -85, 109, -16, -3, 104,
                -95, 68, 54, 34, 26, 114, -1, 106, -121, 3,
                66, 0, 100, -84, 57, 107, 119, -42, 112, -61,
                1, 48, 38, 12, -56, -57, 39, -106, -72, 41,
                7, 71, -29, -59, -8, -38, 79, -31, 124, -124,
                8, 91, 116, 99, -4, 9, -36, -78, 63, -49,
                -67, -87, 59, 101, -32, 92, 94, 53, -41, 115,
                -66, -70, -122, 50, -50, -22, -20, -18, -21, 23,
                -2, -48, 96, 65, -105, 123, -14, -110, 69, -24,
                -120, -75, 74, 127, -60, 113, 90, -114, 105, 46,
                27, -125, -23, -44, 64
        };
        // Thread group for our threads
        ThreadGroup seedGroup;
        // Queue is used to collect seed bytes
        private byte[] pool;
        private int start, end, count;

        /**
         * The constructor is only called once to construct the one
         * instance we actually use. It instantiates the message digest
         * and starts the thread going.
         */

        ThreadedSeedGenerator() {
            pool = new byte[20];
            start = end = 0;

            final ThreadGroup[] finalsg = new ThreadGroup[1];
            Thread t = java.security.AccessController.doPrivileged((PrivilegedAction<Thread>) () -> {
                ThreadGroup parent, group = Thread.currentThread().getThreadGroup();
                while ((parent = group.getParent()) != null) {
                    group = parent;
                }
                finalsg[0] = new ThreadGroup(group, "Dice SeedGenerator ThreadGroup");
                Thread newT = new Thread(finalsg[0],
                        ThreadedSeedGenerator.this,
                        "Dice SeedGenerator Thread");
                newT.setPriority(Thread.MIN_PRIORITY);
                newT.setDaemon(true);
                return newT;
            });
            seedGroup = finalsg[0];
            t.start();
        }

        /**
         * This method does the actual work. It collects random bytes and
         * pushes them into the queue.
         */
        public final void run() {
            try {
                while (true) {
                    // Queue full? Wait till there's room.
                    synchronized (this) {
                        while (count >= pool.length)
                            wait();
                    }

                    int counter, quanta;
                    byte v = 0;

                    // Spin count must not be under 6400
                    for (counter = quanta = 0; (counter < 6400) && (quanta < 6);
                         quanta++) {

                        // Start some noisy threads
                        try {
                            BogusThread bt = new BogusThread();
                            Thread t = new Thread(seedGroup, bt, "Dice SeedGenerator Thread");
                            t.start();
                        } catch (Exception e) {
                            throw new InternalError("internal error: " +
                                    "SeedGenerator thread creation error.");
                        }

                        // We wait 250milli quanta, so the minimum wait time
                        // cannot be under 250milli.
                        int latch = 0;
                        long l = System.currentTimeMillis() + 50;
                        while (System.currentTimeMillis() < l) {
                            synchronized (this) {
                            }
                            latch++;
                        }

                        // Translate the value using the permutation, and xor
                        // it with previous values gathered.
                        v ^= rndTab[latch % 255];
                        counter += latch;
                    }

                    // Push it into the queue and notify anybody who might
                    // be waiting for it.
                    synchronized (this) {
                        pool[end] = v;
                        end++;
                        count++;
                        if (end >= pool.length)
                            end = 0;
                        notifyAll();
                    }
                }
            } catch (Exception e) {
                throw new InternalError("internal error: " +
                        "SeedGenerator thread generated an exception.");
            }
        }

        void getSeedBytes(byte[] result) {
            for (int i = 0; i < result.length; i++) {
                result[i] = getSeedByte();
            }
        }

        byte getSeedByte() {
            byte b = 0;

            try {
                // Wait for it...
                synchronized (this) {
                    while (count <= 0)
                        wait();
                }
            } catch (Exception e) {
                if (count <= 0)
                    throw new InternalError("internal error: " +
                            "SeedGenerator thread generated an exception.");
            }

            synchronized (this) {
                // Get it from the queue
                b = pool[start];
                pool[start] = 0;
                start++;
                count--;
                if (start == pool.length)
                    start = 0;

                // Notify the daemon thread, just in case it is
                // waiting for us to make room in the queue.
                notifyAll();
            }

            return b;
        }

        /**
         * This inner thread causes the thread scheduler to become 'noisy',
         * thus adding entropy to the system load.
         * At least one instance of this class is generated for every seed byte.
         */

        private static class BogusThread implements Runnable {
            public final void run() {
                try {
                    for (int i = 0; i < 5; i++)
                        Thread.sleep(50);
                    // System.gc();
                } catch (Exception ignored) {
                }
            }
        }
    }
}
