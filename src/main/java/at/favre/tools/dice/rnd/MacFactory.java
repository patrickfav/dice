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

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Provider;

/**
 * Creates {@link javax.crypto.Mac} instances
 */
public interface MacFactory {
    /**
     * Creates a new instance of Hmac with given key, i.e. it must already be initialized
     * with {@link Mac#init(Key)}.
     *
     * @param key the key used, must not be null
     * @return a new mac instance
     */
    Mac create(byte[] key);

    /**
     * Default implementation
     */
    final class Default implements MacFactory {
        private final String macAlgorithmName;
        private final Provider provider;

        /**
         * Creates a factory creating HMAC with SHA-256
         *
         * @return factory
         */
        public static MacFactory hmacSha256() {
            return new Default("HmacSHA256", null);
        }

        /**
         * Creates a factory creating HMAC with SHA-512
         *
         * @return factory
         */
        public static MacFactory hmacSha512() {
            return new Default("HmacSHA512", null);
        }

        /**
         * Creates a factory creating HMAC with SHA-1
         *
         * @return factory
         * @deprecated sha1 with HMAC should be fine, but not recommended for new protocols; see <a href="https://crypto.stackexchange.com/questions/26510/why-is-hmac-sha1-still-considered-secure">why-is-hmac-sha1-still-considered-secure</a>
         */
        @Deprecated
        public static MacFactory hmacSha1() {
            return new Default("HmacSHA1", null);
        }

        /**
         * Creates a mac factory
         *
         * @param macAlgorithmName as used by {@link Mac#getInstance(String)}
         */
        public Default(String macAlgorithmName) {
            this(macAlgorithmName, null);
        }

        /**
         * Creates a mac factory
         *
         * @param macAlgorithmName as used by {@link Mac#getInstance(String)}
         * @param provider         what security provider, see {@link Mac#getInstance(String, Provider)}; may be null to use default
         */
        public Default(String macAlgorithmName, Provider provider) {
            this.macAlgorithmName = macAlgorithmName;
            this.provider = provider;
        }

        @Override
        public Mac create(byte[] key) {
            try {
                SecretKey secretKey = new SecretKeySpec(key, macAlgorithmName);

                Mac hmacInstance;

                if (provider == null) {
                    hmacInstance = Mac.getInstance(macAlgorithmName);
                } else {
                    hmacInstance = Mac.getInstance(macAlgorithmName, provider);
                }

                hmacInstance.init(secretKey);
                return hmacInstance;
            } catch (Exception e) {
                throw new IllegalStateException("could not create mac instance", e);
            }
        }
    }
}
