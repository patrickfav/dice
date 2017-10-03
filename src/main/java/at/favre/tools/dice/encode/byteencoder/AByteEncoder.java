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

import at.favre.tools.dice.encode.AEncoder;

import java.math.BigInteger;

public abstract class AByteEncoder extends AEncoder {
    @Override
    public String encodePadded(byte[] array) {
        return encode(array);
    }

    public double spaceEfficiency() {
        return bitPerByte() / 8.0;
    }

    public abstract boolean urlSafe();

    public abstract boolean mayNeedPadding();

    public abstract double bitPerByte();

    int maxLength(byte[] data, int radix) {
        return BigInteger.valueOf(2).pow(BigInteger.valueOf(data.length).multiply(BigInteger.valueOf(8)).intValue()).toString(radix).length();
    }
}
