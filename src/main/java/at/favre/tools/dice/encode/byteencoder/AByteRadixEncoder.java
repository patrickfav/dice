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

import java.math.BigInteger;

public abstract class AByteRadixEncoder extends AByteEncoder {

    abstract int getRadix();

    @Override
    public String encode(byte[] array) {
        return new BigInteger(1, array).toString(getRadix());
    }

    @Override
    public String encodePadded(byte[] array) {
        return String.format("%" + maxLength(array, getRadix()) + "s", encode(array)).replace(' ', '0');
    }


}
