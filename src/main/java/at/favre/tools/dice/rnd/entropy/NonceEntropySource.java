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
import at.favre.tools.dice.util.ByteUtils;

import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Nonce generate as described in SP800-90Ar1. This implementation uses a monotonic sequence number
 * starting with the VM uptime time in millis and the current nano second timestamp
 * <p>
 * See http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf Section 8.6.7.
 */
public final class NonceEntropySource implements ExpandableEntropySource {
    private final static byte[] SALT = new byte[]{0x70, 0x6B, (byte) 0xD8, 0x36, 0x15, 0x1E, (byte) 0xC3, 0x79, 0x09, (byte) 0x8D, (byte) 0xEE, 0x75, 0x6F, (byte) 0xED, 0x05, (byte) 0xC8};

    private long sequenceCounter;

    public NonceEntropySource() {
        sequenceCounter = ManagementFactory.getRuntimeMXBean().getStartTime();
    }

    @Override
    public byte[] generateEntropy(int lengthByte) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES * 4);
        buffer.putLong(++sequenceCounter);
        buffer.putLong(System.nanoTime());
        buffer.putLong(System.currentTimeMillis());
        buffer.putLong(ManagementFactory.getRuntimeMXBean().getUptime());
        return HKDF.fromHmacSha256().extractAndExpand(SALT, buffer.array(), this.getClass().getName().getBytes(StandardCharsets.UTF_8), lengthByte);
    }

    @Override
    public String getInformation() {
        return "Nonce Entropy (" + ByteUtils.bytesToHex(generateEntropy(2)) + ")";
    }
}
