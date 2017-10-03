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

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Uses a strong instance of secure random to generate seeds. This will use the OS entropy pool
 * on most JVMs.
 */
public class SecureRandomEntropySource implements ExpandableEntropySource {
    private final SecureRandom secureRandom;

    public SecureRandomEntropySource() {
        try {
            this.secureRandom = SecureRandom.getInstanceStrong();
            /*
             * Right after the SecureRandom constructor, perform a single nextBytes with some small numBytes > 0
             * and disregard the result (even though 0 should do if a Vulcan implemented the spec), then setSeed()
             * with whatever unpredictable data at hand, then use nextBytes.
             *
             * https://crypto.stackexchange.com/a/51222/44838
             */
            secureRandom.nextBytes(new byte[4]);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("could not get strong secure random instace", e);
        }
    }

    void setSeed(byte[] seed) {
        secureRandom.setSeed(seed);
    }

    @Override
    public byte[] generateEntropy(int length) {
        byte[] out = new byte[length];
        secureRandom.nextBytes(out);
        return out;
    }

    @Override
    public String getInformation() {
        return "SecureRandom: " + secureRandom.getProvider().getInfo() + " (" + secureRandom.getProvider().getName() + "/v" + secureRandom.getProvider().getVersion() + ")";
    }
}
