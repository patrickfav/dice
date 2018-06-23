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
    private final static byte[] SALT = new byte[]{0x29, 0x05, 0x27, 0x2B, (byte) 0xD7, 0x56, (byte) 0x84, 0x27, (byte) 0xD6, (byte) 0xE1, 0x62, 0x4B, (byte) 0xBD, (byte) 0xC9, 0x62, (byte) 0x80};

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
