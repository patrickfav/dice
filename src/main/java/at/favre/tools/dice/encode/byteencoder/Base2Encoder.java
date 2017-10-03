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

import org.apache.commons.codec.binary.BinaryCodec;

import java.util.Arrays;

/**
 * Encodes in to binary representation e.g. <code>010010011</code>
 */
public class Base2Encoder extends AByteEncoder {

    private final static int GROUP_LENGTH = 8;

    @Override
    public String encode(byte[] array) {
        StringBuilder sb = new StringBuilder();
        inPlaceReverse(array); //reverse array to print big endian
        char[] outArray = BinaryCodec.toAsciiChars(array);
        for (int i = 0; i < outArray.length; i += GROUP_LENGTH) {
            sb.append(Arrays.copyOfRange(outArray, i, i + GROUP_LENGTH));

            if (i + GROUP_LENGTH < outArray.length) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private void inPlaceReverse(byte[] validData) {
        for (int i = 0; i < validData.length / 2; i++) {
            byte temp = validData[i];
            validData[i] = validData[validData.length - i - 1];
            validData[validData.length - i - 1] = temp;
        }
    }

    @Override
    public String[] names() {
        return new String[]{"binary", "base2", "bin", "bit"};
    }

    @Override
    public String getDescription() {
        return "A simple binary representation with '0' and '1' divided into 8 bit groups.";
    }

    @Override
    public boolean urlSafe() {
        return false;
    }

    @Override
    public boolean mayNeedPadding() {
        return false;
    }

    @Override
    public double bitPerByte() {
        return 1;
    }
}
