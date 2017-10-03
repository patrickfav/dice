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

import at.favre.tools.dice.ui.Arg;
import org.apache.commons.codec.binary.Hex;

/**
 * Encodes in to base16 (ie. hex) e.g. <code>6e33a8f4c9f69e91</code>
 */
public abstract class Base16Encoder extends AByteEncoder {
    private boolean lowerCase;

    public Base16Encoder(boolean lowerCase) {
        this.lowerCase = lowerCase;
    }

    @Override
    public String encode(byte[] array) {
        return String.valueOf(Hex.encodeHex(array, lowerCase));
    }

    @Override
    public String getDescription() {
        return "Base16 or hex stores each byte as a pair of hexadecimal digits.";
    }

    @Override
    public boolean urlSafe() {
        return true;
    }

    @Override
    public boolean mayNeedPadding() {
        return false;
    }

    @Override
    public double bitPerByte() {
        return 4;
    }

    public static class Base16LowerCaseEncoder extends Base16Encoder {
        public Base16LowerCaseEncoder() {
            super(true);
        }

        @Override
        public String[] names() {
            return new String[]{"base16", Arg.DEFAULT_ENCODING};
        }

        @Override
        public String getDescription() {
            return super.getDescription() + " Lowercase (a-f) letters are used for digits greater than 9.";
        }
    }

    public static class Base16UpperCaseEncoder extends Base16Encoder {
        public Base16UpperCaseEncoder() {
            super(false);
        }

        @Override
        public String[] names() {
            return new String[]{"BASE16", "HEX"};
        }

        @Override
        public String getDescription() {
            return super.getDescription() + " Uppercase (A-F) letters are used for digits greater than 9.";
        }
    }
}
