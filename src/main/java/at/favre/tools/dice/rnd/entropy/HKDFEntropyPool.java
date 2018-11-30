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
import at.favre.tools.dice.rnd.EntropyPool;
import at.favre.tools.dice.rnd.ExpandableEntropySource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 * Default implementation of entropy pool using KDF method HKDF to generate uniformly distributed entropy
 * bit strings.
 */
public final class HKDFEntropyPool implements EntropyPool {
    private static final int DEFAULT_INTERNAL_SEED_LENGTH = 32;
    private static final byte[] SALT = new byte[]{0x0B, 0x40, (byte) 0xC3, (byte) 0x91, 0x73, (byte) 0x88, 0x58, 0x6A, 0x22, (byte) 0xD4, (byte) 0xC3,
            0x04, 0x16, 0x1F, (byte) 0xB9, (byte) 0xE7, 0x21, 0x50, 0x4E, (byte) 0x8A, (byte) 0xDC, 0x0D, 0x6C, (byte) 0xCC, (byte) 0x90, 0x5A, 0x7A,
            0x1C, 0x2A, (byte) 0xBB, 0x38, (byte) 0xE8};

    private final List<ExpandableEntropySource> entropySourceList = new LinkedList<>();

    private final int sourceSeedLengthBytes;

    public HKDFEntropyPool() {
        this(DEFAULT_INTERNAL_SEED_LENGTH);
    }

    /**
     * Create a new entropy pool
     *
     * @param sourceSeedLengthBytes this many bytes will be requested by each entropy source; will internally at least take 16 bytes
     */
    public HKDFEntropyPool(int sourceSeedLengthBytes) {
        this.sourceSeedLengthBytes = Math.max(16, sourceSeedLengthBytes);
    }

    @Override
    public void add(ExpandableEntropySource source) {
        entropySourceList.add(source);
    }

    @Override
    public String getInformation() {
        StringBuilder sb = new StringBuilder();
        entropySourceList.forEach(e -> sb.append(e.getInformation()).append('\n'));
        return sb.toString();
    }

    @Override
    public byte[] generateEntropy(int lengthByte) {
        if (entropySourceList.isEmpty()) {
            throw new IllegalStateException("entropy pool must not be empty - add entropy sources first");
        }

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        entropySourceList.forEach(source -> {
            try {
                bos.write(source.generateEntropy(sourceSeedLengthBytes));
            } catch (IOException e) {
                throw new IllegalStateException("could not generate seed in pool", e);
            }
        });
        return HKDF.fromHmacSha512().extractAndExpand(SALT, bos.toByteArray(), this.getClass().getName().getBytes(StandardCharsets.UTF_8), lengthByte);
    }
}
