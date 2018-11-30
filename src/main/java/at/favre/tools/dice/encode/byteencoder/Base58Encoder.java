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

package at.favre.tools.dice.encode.byteencoder;

import at.favre.tools.dice.encode.impl.Base58;

/**
 * Base58 is a group of binary-to-text encoding schemes used to represent large integers as alphanumeric text.
 * It is similar to Base64 but has been modified to avoid both non-alphanumeric characters and letters which might
 * look ambiguous when printed. It is therefore designed for human users who manually enter the data, copying from
 * some visual source, but also allows easy copy and paste because a double-click will usually select the whole string.
 */
public abstract class Base58Encoder extends AByteEncoder {

    @Override
    public String[] names() {
        return new String[]{"base58"};
    }

    @Override
    public String getDescription() {
        return "Base58 is similar to Base64 but has been modified to avoid both non-alphanumeric characters and letters which might look ambiguous when printed.";
    }

    @Override
    public boolean urlSafe() {
        return true;
    }

    @Override
    public boolean mayNeedPadding() {
        return true;
    }

    @Override
    public double bitPerByte() {
        return 5.85798;
    }

    /**
     * Encodes in to base58 like used by bitcoin e.g. <code>bXz5Mc9JLEFRhtiZhoQF4s5V2PHNVwUEi1KoSx89eNg</code>
     */
    public static class BitcoinStyle extends Base58Encoder {
        @Override
        public String encode(byte[] array) {
            return Base58.BitcoinEncoder().encode(array);
        }

        @Override
        public String getDescription() {
            return super.getDescription() + " This version uses the alphabet common for Bitcoin protocol.";
        }
    }
}
