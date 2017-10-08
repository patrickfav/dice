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
import at.favre.tools.dice.util.ByteUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public abstract class AEntropySourceTest {
    abstract ExpandableEntropySource getExpandableEntropySource();

    @Test
    public void generateExpandableEntropyTest() throws Exception {
        ExpandableEntropySource entropySource = getExpandableEntropySource();
        if (entropySource == null) {
            return;
        }

        assertNotNull(entropySource.getInformation());

        Set<byte[]> pastSeeds = new HashSet<>();
        for (int i = 8; i < 64; i += 8) {
            byte[] seed = entropySource.generateEntropy(i);
            assertTrue(seed.length == i);
            assertFalse(pastSeeds.contains(seed));
            pastSeeds.add(seed);

            System.out.println(ByteUtils.bytesToHex(seed));
        }
    }
}
