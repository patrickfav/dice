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

import at.favre.tools.dice.rnd.entropy.FixedEntropySource;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;

class AHmacDrbgNistTestVectorsTest {

    void testDrbg(MacFactory macFactory, byte[] entropy, byte[] nonce, byte[] perso, byte[] expected, int returnedBitsLength) {
        HmacDrbg drbg = new HmacDrbg(new DrbgParameter(macFactory,
                new FixedEntropySource(entropy),
                new FixedEntropySource(nonce), perso));
        byte[] out1 = new byte[returnedBitsLength / 8];
        drbg.nextBytes(out1);
        byte[] out2 = new byte[returnedBitsLength / 8];
        drbg.nextBytes(out2);
        byte[] out3 = new byte[returnedBitsLength / 8];
        drbg.nextBytes(out3);

        assertFalse(Arrays.equals(expected, out1));
        assertFalse(Arrays.equals(out1, out3));
        assertFalse(Arrays.equals(out2, out3));

        assertArrayEquals(expected, out2);
    }

    void testDrbgReseed(MacFactory macFactory, byte[] entropy, byte[] reseedEntropy, byte[] nonce, byte[] perso, byte[] additionalInfo, byte[] expected, int returnedBitsLength) {
        HmacDrbg drbg = new HmacDrbg(new DrbgParameter(macFactory,
                new FixedEntropySource(entropy, reseedEntropy),
                new FixedEntropySource(nonce, new byte[0]),
                perso));
        drbg.requestReseed(additionalInfo);

        byte[] out1 = new byte[returnedBitsLength / 8];
        drbg.nextBytes(out1);
        byte[] out2 = new byte[returnedBitsLength / 8];
        drbg.nextBytes(out2);
        byte[] out3 = new byte[returnedBitsLength / 8];
        drbg.nextBytes(out3);

        assertFalse(Arrays.equals(expected, out1));
        assertFalse(Arrays.equals(out1, out3));
        assertFalse(Arrays.equals(out2, out3));

        assertArrayEquals(expected, out2);
    }

    protected byte[] hex(String hex) {
        try {
            return Hex.decodeHex(hex.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalStateException(e);
        }
    }
}