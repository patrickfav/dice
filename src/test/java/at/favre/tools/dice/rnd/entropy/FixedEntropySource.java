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

import at.favre.tools.dice.rnd.ExpandableEntropySource;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class FixedEntropySource implements ExpandableEntropySource {
    private final Queue<byte[]> deterministicEntropyQueue = new LinkedList<>();

    public FixedEntropySource(byte[] entropy) {
        this.deterministicEntropyQueue.add(entropy);
    }

    public FixedEntropySource(byte[] entropy, byte[]... moreEntropy) {
        this.deterministicEntropyQueue.add(entropy);
        if (moreEntropy != null) {
            this.deterministicEntropyQueue.addAll(Arrays.asList(moreEntropy));
        }
    }

    @Override
    public byte[] generateEntropy(int lengthByte) {
        if (deterministicEntropyQueue.isEmpty()) {
            return new byte[0];
        } else {
            return deterministicEntropyQueue.remove();
        }
    }

    @Override
    public String getInformation() {
        return "FixedEntropySource (" + deterministicEntropyQueue.hashCode() + ")";
    }
}
