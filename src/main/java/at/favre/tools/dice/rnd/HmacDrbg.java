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
import org.jetbrains.annotations.Nullable;

import javax.crypto.Mac;
import java.util.Arrays;

/**
 * Deterministic Random Bit Generator based on any HMAC implementation available to {@link Mac}
 * <p>
 * Also known as: HMAC_DRBG.
 * See http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf for thorough specification.
 * <p>
 * Reseeding and additional info is supported.
 * <p>
 * See http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf Section 8.6.8.
 */
public final class HmacDrbg implements DeterministicRandomBitGenerator {

    // floor(7500/8); see: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf D.2 #5.
    private static final int MAX_BYTES_PER_REQUEST = 937;
    private static final byte[] BYTE_ARRAY_0 = {0};
    private static final byte[] BYTE_ARRAY_1 = {1};
    private final DrbgParameter paramter;

    // "V" from the the spec.
    private byte[] value;
    // An instance of HMAC configured with "Key" from the spec.
    private Mac hmac;
    // The total number of bytes that have been generated from this DRBG so far.
    private int bytesGenerated;

    /**
     * For in-depth description of the parameters, see the NIST spec.
     * <p>
     * The constructor's entropyInput should contain security_strength bits many high quality random bytes.
     * HMAC_DRBG requires entropy input to be security_strength bits long.
     * <p>
     * HMAC_DRBG requires nonce to be at least 1/2 security_strength bits long.
     * See: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf
     *
     * @param drbgParameter parameter defining this DRBG
     */
    public HmacDrbg(DrbgParameter drbgParameter) {
        this.paramter = drbgParameter;

        // HMAC_DRBG Instantiate Process
        // See: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf 10.1.1.2

        // 2. Key = 0x00 00...00
        initHmac(new byte[getSecurityStrengthBytes()]);
        // 3. V = 0x01 01...01
        value = new byte[getSecurityStrengthBytes()];
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
                paramter.entropySource.generateEntropy(getSecurityStrengthBytes()),
                paramter.nonceSource.generateEntropy(getSecurityStrengthBytes() / 2),
                paramter.personalizationString == null ? new byte[0] : paramter.personalizationString);
    }

    /**
     * Set's the "Key" state from the spec.
     */
    private void initHmac(byte[] key) {
        hmac = paramter.macFactory.create(key);
    }

    /**
     * HMAC_DRBG Update Process
     * <p>
     * See: http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf 10.1.2.2
     */
    private void hmacDrbgUpdate(byte[] providedData) {
        // 1. K = HMAC(K, V || 0x00 || provided_data)
        initHmac(hmac.doFinal(ByteUtils.concatAll(value, BYTE_ARRAY_0, emptyIfNull(providedData))));
        // 2. V = HMAC(K, V);
        value = hmac.doFinal(value);
        // 3. If (provided_data = Null), then return K and V.
        if (providedData == null) {
            return;
        }
        // 4. K = HMAC (K, V || 0x01 || provided_data).
        initHmac(hmac.doFinal(ByteUtils.concatAll(value, BYTE_ARRAY_1, providedData)));
        // 5. V = HMAC (K, V).
        value = hmac.doFinal(value);
    }

    /**
     * Request reseeding of this HMAC_DRBG
     * <p>
     * This uses the provided entropy sources as well as an optional additionalInfo
     */
    @Override
    public void requestReseed(@Nullable byte[] additionalInfo) {
        hmacDrbgReseed(paramter.entropySource.generateEntropy(getSecurityStrengthBytes()), paramter.nonceSource.generateEntropy(getSecurityStrengthBytes() / 2), additionalInfo);
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
     *
     * @param out             target byte array to write random bytes
     * @param start           where to start to write in out
     * @param count           how many bytes from start
     * @param additionalInput The additional input string received from the consuming application. Note that the length of the additional_input string may be zero or null
     */
    private void hmacDrbgGenerate(byte[] out, int start, int count, byte[] additionalInput) {
        // 3. temp = Null.
        int bytesWritten = 0;
        // 4. While (len (temp) < requested_number_of_bits) do:
        while (bytesWritten < count) {
            // 4.1 V = HMAC (Key, V).
            value = hmac.doFinal(value);
            // 4.2 temp = temp || V.
            // 5. returned_bits = Leftmost requested_number_of_bits of temp
            int bytesToWrite = Math.min(count - bytesWritten, getSecurityStrengthBytes());
            System.arraycopy(value, 0, out, start + bytesWritten, bytesToWrite);
            bytesWritten += bytesToWrite;
        }

        // 6. (Key, V) = HMAC_DRBG_Update (additional_input, Key, V).
        hmacDrbgUpdate(additionalInput);
    }

    /**
     * Security Strength in Bits; depending on the used HMAC hash
     *
     * @return bit length
     */
    private int getSecurityStrengthBytes() {
        return paramter.securityStrengthBit / 8;
    }

    /**
     * Returns the next length pseudo-random bytes.
     */
    @Override
    public byte[] nextBytes(int lengthBytes) {
        byte result[] = new byte[lengthBytes];
        nextBytes(result, null);
        return result;
    }

    /**
     * Fills the output vector with pseudo-random bytes.
     */
    @Override
    public void nextBytes(byte[] out, byte[] additionalInfo) {
        generateBytesProcess(out, 0, out.length, additionalInfo);
    }

    /**
     * Fills out[start] through out[start+count-1] (inclusive) with pseudo-random bytes.
     * <p>
     * See NIST.SP.800-90Ar1 10.1.2.5 (HMAC_DRBG Generate Process)
     *
     * @param additionalInput may be null
     */
    private void generateBytesProcess(byte[] out, int start, int count, byte[] additionalInput) {
        if (count == 0) {
            return;
        }

        if (additionalInput != null) {
            hmacDrbgUpdate(additionalInput);
        }

        if (paramter.reseedAllowed && bytesGenerated + count > paramter.reseedIntervalByte) {
            requestReseed(null);
        }

        try {
            int bytesWritten = 0;
            while (bytesWritten < count) {
                int bytesToWrite = Math.min(count - bytesWritten, MAX_BYTES_PER_REQUEST);
                hmacDrbgGenerate(out, start + bytesWritten, bytesToWrite, additionalInput);
                bytesWritten += bytesToWrite;
            }
        } finally {
            bytesGenerated += count;
        }
    }
}
