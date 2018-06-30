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
import at.favre.lib.crypto.HKDF;
import at.favre.tools.dice.rnd.ExpandableEntropySource;

import java.nio.charset.StandardCharsets;

/**
 * Used with external seeds that are estimated to be strong. The strong seed will
 * used in quasi directly (only HKDF after each call will be preformed additional to
 * adding an monotonic counter, to generate different outputs each call)
 */
public final class ExternalStrongSeedEntropySource implements ExpandableEntropySource {
    private final static byte[] SALT = new byte[]{0x57, 0x58, 0x6E, (byte) 0x9A, 0x7C, (byte) 0xE4, 0x2E, 0x57, 0x61, 0x07, 0x18, (byte) 0xD9, (byte) 0x90, (byte) 0xFE, (byte) 0xC7, 0x2A, (byte) 0xA7, (byte) 0xEE, (byte) 0xFD, 0x10, 0x38, (byte) 0x87, 0x19, (byte) 0x85, (byte) 0xA1, 0x71, 0x3E, 0x31, 0x41, 0x74, 0x60, 0x66};

    private byte[] internalSeed;
    private long counter = Long.MIN_VALUE;

    public ExternalStrongSeedEntropySource(String seed) {
        this(seed.getBytes(StandardCharsets.UTF_8));
    }

    public ExternalStrongSeedEntropySource(byte[] seed) {
        regenerateInternalSeed(seed);
    }

    private void regenerateInternalSeed(byte[] seed) {
        if (counter >= Long.MAX_VALUE) {
            throw new IllegalStateException("counter reached max value (2^64)");
        }

        internalSeed = HKDF.fromHmacSha512().extract(SALT, Bytes.from(seed).append(counter++).array());

    }

    @Override
    public byte[] generateEntropy(int length) {
        byte[] out = HKDF.fromHmacSha512().expand(
                internalSeed, this.getClass().getName().getBytes(StandardCharsets.UTF_8), length);
        regenerateInternalSeed(internalSeed);
        return out;
    }

    @Override
    public String getInformation() {
        return "External Seed Entropy Source (" + Bytes.from(generateEntropy(2)).encodeHex(true) + ")";
    }
}
