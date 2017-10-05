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

import at.favre.tools.dice.util.ByteUtils;

import javax.crypto.Mac;
import java.util.Arrays;

/**
 * Deterministic Random Bit Generator based on HMAC-SHA256.
 * <p>
 * Also known as: HMAC_DRBG.
 * See http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf for thorough specification.
 * <p>
 * Reseeding is supported.
 * See http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf Section 8.6.8.
 */
public final class HmacDrbg implements DeterministicRandomBitGenerator {
    // Assume maximum security strength for HMAC-512, which is 512.
    // See: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf D.2 #1.
    private static final int SECURITY_STRENGTH_BIT = 512;

    /**
     * The constructor's entropyInput should contain this many high quality random bytes.
     * HMAC_DRBG requires entropy input to be security_strength bits long.
     * See: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf
     */
    private static final int ENTROPY_INPUT_SIZE_BYTES = SECURITY_STRENGTH_BIT / 8;
    /**
     * HMAC_DRBG requires nonce to be at least 1/2 security_strength bits long.
     * See: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf
     */
    private static final int NONCE_INPUT_SIZE_BYTES = (SECURITY_STRENGTH_BIT / 8) / 2;
    /**
     * Personalization strings should not exceed this many bytes in length.
     * <p>
     * See: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf D.2 #7.
     */
    private static final int PERSONALIZATION_STRING_LENGTH_BYTES = (SECURITY_STRENGTH_BIT / 8) / 2;
    /**
     * The maximum number of bytes that can be generated from this DRBG with a single seed.
     * <p>
     * This is conservative relative to the suggestions in
     * http://csrc.nist.gov/publications/nistpubs/800-90A/SP800-90A.pdf section D.2.
     * Reseeding is necessary when this threshold is reached.
     */
    // See: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf D.2 #2.
    private static final int DIGEST_NUM_BYTES = SECURITY_STRENGTH_BIT / 8;
    // floor(7500/8); see: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf D.2 #5.
    private static final int MAX_BYTES_PER_REQUEST = 937;
    private static final byte[] BYTE_ARRAY_0 = {0};
    private static final byte[] BYTE_ARRAY_1 = {1};
    private final DrbgParameter paramter;

    // "V" from the the spec.
    private byte[] value;
    // An instance of HMAC-SHA512 configured with "Key" from the spec.
    private Mac hmac;
    // The total number of bytes that have been generated from this DRBG so far.
    private int bytesGenerated;

    /**
     * For in-depth description of the parameters, see the NIST spec.
     *
     * @param drbgParameter parameter defining this DRBG
     */
    public HmacDrbg(DrbgParameter drbgParameter) {
        this.paramter = drbgParameter;

        // HMAC_DRBG Instantiate Process
        // See: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf 10.1.1.2

        // 2. Key = 0x00 00...00
        setKey(new byte[paramter.securityStrengthBit / 8]);
        // 3. V = 0x01 01...01
        value = new byte[DIGEST_NUM_BYTES];
        Arrays.fill(value, (byte) 0x01);

        // 4. (Key, V) = HMAC_DRBG_Update(seed_material, Key, V)
        hmacDrbgUpdate(generateSeedMaterial());
        bytesGenerated = 0;
    }

    /**
     * Returns an 0-length byte array if b is null, otherwise returns b.
     */
    private static byte[] emptyIfNull(byte[] b) {
        return b == null ? new byte[0] : b;
    }


    private byte[] generateSeedMaterial() {
        // 1. seed_material = entropy_input + nonce + personalization_string
        // Note: We are using the 8.6.7 interpretation, where the entropy_input and
        // nonce are acquired at the same time from the same source.
        return ByteUtils.concatAll(
                paramter.entropySource.generateEntropy(ENTROPY_INPUT_SIZE_BYTES),
                paramter.nonceSource.generateEntropy(NONCE_INPUT_SIZE_BYTES),
                paramter.personalizationString);
    }

    /**
     * Set's the "Key" state from the spec.
     */
    private void setKey(byte[] key) {
        hmac = paramter.macFactory.create(key);
    }

    /**
     * Computes hmac("key" from the spec, x).
     */
    private byte[] hash(byte[] x) {
        hmac.update(x);
        return hmac.doFinal();
    }

    /**
     * HMAC_DRBG Update Process
     * <p>
     * See: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf 10.1.2.2
     */
    private void hmacDrbgUpdate(byte[] providedData) {
        // 1. K = HMAC(K, V || 0x00 || provided_data)
        setKey(hash(ByteUtils.concatAll(value, BYTE_ARRAY_0, emptyIfNull(providedData))));
        // 2. V = HMAC(K, V);
        value = hash(value);
        // 3. If (provided_data = Null), then return K and V.
        if (providedData == null) {
            return;
        }
        // 4. K = HMAC (K, V || 0x01 || provided_data).
        setKey(hash(ByteUtils.concatAll(value, BYTE_ARRAY_1, providedData)));
        // 5. V = HMAC (K, V).
        value = hash(value);
    }

    /**
     * HMAC_DRBG Reseed Process
     * <p>
     * Let HMAC_DRBG_Update be the function specified in Section
     * 10.1.2.2. The following process or its equivalent shall be used
     * as the reseed algorithm for this DRBG mechanism (see step 6 of
     * the reseed process in Section 9.2):
     *
     * @param entropyInput   The string of bits obtained from the randomness source
     * @param additionalData The additional input string received from the consuming application. Note that the length of the additional_input string may be zero or null
     */
    private void hmacDrbgReseed(byte[] entropyInput, byte[] nonce, byte[] additionalData) {

        //1. seed_material = entropy_input || additional_input.
        byte[] seedMaterial = ByteUtils.concatAll(entropyInput, nonce, emptyIfNull(additionalData));
        //2. (Key, V) = HMAC_DRBG_Update (seed_material, Key, V)
        hmacDrbgUpdate(seedMaterial);
        //3. reseed_counter = 1.
        bytesGenerated = 0;
    }

    /**
     * HMAC_DRBG Generate Process
     * <p>
     * See: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf 10.1.2.5
     * <p>
     * We do not support additional_input, assuming it to be always null.
     * <p>
     * We guarantee that reseeding is never required through the use of currentByteSeedInterval
     * rather than RESEED_INTERVAL.
     */
    private void hmacDrbgGenerate(byte[] out, int start, int count) {
        // 3. temp = Null.
        int bytesWritten = 0;
        // 4. While (len (temp) < requested_number_of_bits) do:
        while (bytesWritten < count) {
            // 4.1 V = HMAC (Key, V).
            value = hash(value);
            // 4.2 temp = temp || V.
            // 5. returned_bits = Leftmost requested_number_of_bits of temp
            int bytesToWrite = Math.min(count - bytesWritten, DIGEST_NUM_BYTES);
            System.arraycopy(value, 0, out, start + bytesWritten, bytesToWrite);
            bytesWritten += bytesToWrite;
        }

        // 6. (Key, V) = HMAC_DRBG_Update (additional_input, Key, V).
        hmacDrbgUpdate(null);
    }

    /**
     * Request reseeding of this HMAC_DRBG
     */
    public void requestReseed(byte[] additionalInfo) {
        hmacDrbgReseed(paramter.entropySource.generateEntropy(ENTROPY_INPUT_SIZE_BYTES), paramter.nonceSource.generateEntropy(NONCE_INPUT_SIZE_BYTES), additionalInfo);
    }

    /**
     * Returns the next length pseudo-random bytes.
     */
    @Override
    public byte[] nextBytes(int lengthBytes) {
        byte result[] = new byte[lengthBytes];
        nextBytes(result);
        return result;
    }

    /**
     * Fills the output vector with pseudo-random bytes.
     */
    @Override
    public void nextBytes(byte[] out) {
        nextBytes(out, 0, out.length);
    }

    /**
     * Fills out[start] through out[start+count-1] (inclusive) with pseudo-random bytes.
     */
    public void nextBytes(byte[] out, int start, int count) {
        if (count == 0) {
            return;
        }

        if (paramter.reseedAllowed && bytesGenerated + count > paramter.reseedIntervalByte) {
            requestReseed(null);
        }

        try {
            int bytesWritten = 0;
            while (bytesWritten < count) {
                int bytesToWrite = Math.min(count - bytesWritten, MAX_BYTES_PER_REQUEST);
                hmacDrbgGenerate(out, start + bytesWritten, bytesToWrite);
                bytesWritten += bytesToWrite;
            }
        } finally {
            bytesGenerated += count;
        }
    }
}
