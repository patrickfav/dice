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

package at.favre.tools.dice.util;


import java.util.HashMap;
import java.util.Map;

/**
 * Calculate the entropy of a discrete distribution of <T>.
 *
 * @author Gilad Mishne
 */
public final class Entropy<T> {
    private final Map<T, Integer> map = new HashMap<>();
    private int total = 0;

    private static double Log2(double n) {
        return Math.log(n) / Math.log(2);
    }

    public Entropy(Iterable<T> elements) {
        for (T element : elements) {
            if (!map.containsKey(element)) {
                map.put(element, 0);
            }
            map.put(element, map.get(element) + 1);
            total++;
        }
    }

    public double entropy() {
        double entropy = 0;
        for (int count : map.values()) {
            double prob = (double) count / total;
            entropy -= prob * Log2(prob);
        }
        return entropy;
    }

    public double perplexity() {
        return Math.pow(2, entropy());
    }
}