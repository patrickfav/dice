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

public class DrbgParameter {
    private static final int MAX_BYTES_PER_SEED = 1024 * 1024 * 1024;

    public final MacFactory macFactory;
    public final int securityStrengthBit;
    public final ExpandableEntropySource entropySource;
    public final ExpandableEntropySource nonceSource;
    public final byte[] personalizationString;
    public final boolean reseedAllowed;
    public final int reseedIntervalByte;

    public DrbgParameter(MacFactory macFactory, ExpandableEntropySource entropySource, ExpandableEntropySource nonceSource, byte[] personalizationString, boolean reseedAllowed, int reseedIntervalByte) {
        this.macFactory = macFactory;
        this.securityStrengthBit = macFactory.create(new byte[1]).getMacLength() * 8;
        this.entropySource = entropySource;
        this.nonceSource = nonceSource;
        this.personalizationString = personalizationString;
        this.reseedAllowed = reseedAllowed;
        this.reseedIntervalByte = reseedIntervalByte;
    }

    public DrbgParameter(MacFactory macFactory, ExpandableEntropySource entropySource, ExpandableEntropySource nonceSource, byte[] personalizationString, boolean reseedAllowed) {
        this(macFactory, entropySource, nonceSource, personalizationString, reseedAllowed, MAX_BYTES_PER_SEED);
    }

    public DrbgParameter(MacFactory macFactory, ExpandableEntropySource entropySource, ExpandableEntropySource nonceSource, byte[] personalizationString) {
        this(macFactory, entropySource, nonceSource, personalizationString, true, MAX_BYTES_PER_SEED);
    }
}
