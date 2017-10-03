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

package at.favre.tools.dice.service.randomorg;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util for verifying the signature format of random.org's signed request
 * <p>
 * See https://api.random.org/verify/manual
 */
final class RandomOrgUtil {
    static final String RANDOM_ORG_PUB_KEY = "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";

    private RandomOrgUtil() {
    }

    /**
     * Parses the correct property from the raw response and verifies it against random.org
     * public key
     *
     * @param rawResponse the whole raw response as string
     * @param base64Sig   base64 encoded signature from the server response
     * @return true if could be verified
     */
    static boolean verifySignature(String rawResponse, String base64Sig) {
        try {
            Pattern p = Pattern.compile("\"random\"\\s*:\\s*(\\{.+?})\\s*,");
            Matcher matcher = p.matcher(rawResponse);
            return matcher.find() && verifyRSA(parsePublicKey(new Base64().decode(RANDOM_ORG_PUB_KEY)), matcher.group(1).getBytes(StandardCharsets.UTF_8), new Base64().decode(base64Sig));
        } catch (Exception e) {
            throw new IllegalStateException("could not verify signature", e);
        }
    }

    /**
     * Checks the signature based on given pub key and raw data
     *
     * @param publicKey used to verify the signature
     * @param plainData the plain data to verify
     * @param signature as byte array
     * @return true if the signature can be verified with given data
     */
    static boolean verifyRSA(PublicKey publicKey, byte[] plainData, byte[] signature) {
        try {
            Signature sig = Signature.getInstance("SHA512withRSA");
            sig.initVerify(publicKey);
            sig.update(plainData);
            return sig.verify(signature);
        } catch (Exception e) {
            throw new IllegalStateException("could not verify", e);
        }
    }

    /**
     * Parses a public key in X.509 format
     *
     * @param keyBytes the raw bytes of the certificate
     * @return the java POJO pub key
     * @throws Exception if the certificate could not be parsed
     */
    static PublicKey parsePublicKey(byte[] keyBytes) throws Exception {
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        X509Certificate cer = (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(keyBytes));
        return cer.getPublicKey();
    }

    static byte[] sha512(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            return md.digest(raw.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("could not hash", e);
        }
    }

    static String base64EncodedSha512(String raw) {
        return new Base64().encodeToString(sha512(raw));
    }
}