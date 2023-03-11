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

import at.favre.lib.bytes.Bytes;
import at.favre.lib.hkdf.HKDF;
import at.favre.tools.dice.rnd.ExpandableEntropySource;

import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Nonce generate as described in SP800-90Ar1. This implementation uses a monotonic sequence number
 * starting with the VM uptime time in millis and the current nanosecond timestamp
 * <p>
 * See <a href="http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf">NIST.SP.800-90Ar1</a> Section 8.6.7.
 */
public final class NonceEntropySource implements ExpandableEntropySource {
    private static final byte[] SALT = new byte[]{0x48, (byte) 0xB8, (byte) 0x96, (byte) 0xC6, (byte) 0x87, 0x5C, (byte) 0xD0, (byte) 0xF9, (byte) 0x9D,
            0x60, (byte) 0xFA, 0x11, 0x3E, 0x68, 0x2A, (byte) 0xD0, 0x48, (byte) 0xE3, (byte) 0x83, (byte) 0x90, (byte) 0xA2, 0x3E, 0x79, (byte) 0xCB,
            0x52, (byte) 0xBF, (byte) 0xB9, 0x4C, 0x67, 0x1B, (byte) 0xEF, 0x49};

    private long sequenceCounter;
    private long lastAccess = System.nanoTime();

    public NonceEntropySource() {
        sequenceCounter = ManagementFactory.getRuntimeMXBean().getStartTime();
    }

    @Override
    public byte[] generateEntropy(int lengthByte) {
        if (sequenceCounter == Long.MAX_VALUE) {
            throw new IllegalStateException("sequence counter reached max value (2^64)");
        }

        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES * 5);
        buffer.putLong(++sequenceCounter);
        buffer.putLong(System.nanoTime());
        buffer.putLong(System.currentTimeMillis());
        buffer.putLong(ManagementFactory.getRuntimeMXBean().getUptime());
        buffer.putLong(System.nanoTime() - lastAccess);

        lastAccess = System.nanoTime();

        return HKDF.fromHmacSha256().extractAndExpand(SALT, buffer.array(), this.getClass().getName().getBytes(StandardCharsets.UTF_8), lengthByte);
    }

    @Override
    public String getInformation() {
        return "Nonce Entropy (" + Bytes.from(generateEntropy(2)).encodeHex(true) + ")";
    }
}
