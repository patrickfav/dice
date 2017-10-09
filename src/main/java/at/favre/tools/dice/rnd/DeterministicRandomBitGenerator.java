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

import org.jetbrains.annotations.Nullable;

/**
 * A Deterministic Random Bit Generator as defined by NIST, will output
 * pseudo random data depending on a given seed.
 */
public interface DeterministicRandomBitGenerator {

    /**
     * Requests a reseed of this DRBG. Uses the internal entropy sources,
     * provided through {@link DrbgParameter}
     *
     * @param additionalInfo optional parameter to increase the security cushion see NIST SP 800-90Ar1 Section 8.7.2
     */
    void requestReseed(@Nullable byte[] additionalInfo);

    /**
     * Get the next pseudo random data
     *
     * @param lengthBytes output length in byte
     * @return new byte array with the random data
     */
    byte[] nextBytes(int lengthBytes);

    /**
     * Get the next pseudo random data.
     *
     * @param out            fills this byte array with data
     * @param additionalInfo can be null, additional information that is used to increase the security cushion,
     *                       see NIST SP 800-90Ar1 Section 8.7.2
     */
    void nextBytes(byte[] out, @Nullable byte[] additionalInfo);
}
