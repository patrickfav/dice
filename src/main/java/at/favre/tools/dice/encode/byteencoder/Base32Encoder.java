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

import org.apache.commons.codec.binary.Base32;

/**
 * Encodes in to base32 e.g. <code>NHSMJ5VH6P5QRAT4EXAP2BDZ5Q</code>
 */
public class Base32Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return encodePadded(array).replace("=", "");
    }

    @Override
    public String encodePadded(byte[] array) {
        return new Base32((byte) '=').encodeAsString(array);
    }

    @Override
    public String[] names() {
        return new String[]{"base32"};
    }

    @Override
    public String getDescription() {
        return "Base32 uses a 32-character subset of the twenty-six letters A-Z and ten digits 0-9. Uses the alphabet defined in RFC 4648.";
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
        return 5;
    }
}
