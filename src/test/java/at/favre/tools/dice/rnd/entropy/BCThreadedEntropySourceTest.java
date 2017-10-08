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
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;

public class BCThreadedEntropySourceTest extends AEntropySourceTest {
    @Test
    public void generateEntropy() throws Exception {
        BCThreadedEntropySource source = new BCThreadedEntropySource();

        Thread.currentThread().sleep(25);

        byte[] seed = source.generateEntropy(1024);
        byte[] seed1 = source.generateEntropy(1024);
        byte[] seed2 = source.generateEntropy(1024);
        byte[] seed3 = source.generateEntropy(1024);

        assertFalse(Arrays.equals(seed, seed1));
        assertFalse(Arrays.equals(seed1, seed2));
        assertFalse(Arrays.equals(seed1, seed3));
        assertFalse(Arrays.equals(seed2, seed3));

//        System.out.println(Hex.encodeHex(seed));
//        System.out.println(Hex.encodeHex(seed1));
//        System.out.println(Hex.encodeHex(seed2));
//        System.out.println(Hex.encodeHex(seed3));
    }

    @Override
    ExpandableEntropySource getExpandableEntropySource() {
        return new BCThreadedEntropySource();
    }
}