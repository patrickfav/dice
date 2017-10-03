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

/**
 * A Deterministic Random Bit Generator as defined by NIST, will output
 * pseudo random data depending on a given seed.
 */
public interface DeterministicRandomBitGenerator {

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
     * @param out fills this byte array with data
     */
    void nextBytes(byte[] out);
}
