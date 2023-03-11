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

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Used for weak external entropy source like a user input. The seed will be extracted with HKDF
 * and combined with a{@link SecureRandom} instance which itself seeds with OS entropy pool,
 * therefore mixing the weaker source with a stronger, unpredictable one.
 */
public final class ExternalWeakSeedEntropySource extends SecureRandomEntropySource {
    private static final byte[] SALT = new byte[]{(byte) 0x80, (byte) 0xFD, (byte) 0xDE, 0x4B, (byte) 0xFB, (byte) 0xC9, 0x48, 0x02, (byte) 0xDF, 0x47,
            (byte) 0x95, (byte) 0xE5, (byte) 0xB1, 0x24, (byte) 0x9C, 0x3F, 0x4A, 0x5C, 0x16, 0x03, 0x0D, 0x18, 0x3B, (byte) 0xB5, 0x3E, 0x26, (byte) 0xBF,
            (byte) 0xF5, 0x09, 0x79, 0x5F, (byte) 0xA1};

    public ExternalWeakSeedEntropySource(String seed) {
        this(seed.getBytes(StandardCharsets.UTF_8));
    }

    public ExternalWeakSeedEntropySource(long seed) {
        this(Bytes.from(seed).array());
    }

    public ExternalWeakSeedEntropySource(byte[] seed) {
        super();
        setSeed(HKDF.fromHmacSha256().extract(SALT, seed));
    }

    @Override
    public String getInformation() {
        return super.getInformation() + " (seeded)";
    }
}
