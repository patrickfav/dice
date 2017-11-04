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

package at.favre.tools.dice.encode.languages;

import at.favre.lib.bytes.Bytes;

/**
 * Encodes in kotlin syntax byte array (e.g. <code>byteArrayOf(0x3A,...)</code>)
 */
public class KotlinByteArrayEncoder extends AProgrammingLanguagesEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "byteArrayOf(", ")", ",", b -> {
            StringBuilder sb = new StringBuilder();
            sb.append("0x").append(Bytes.from(b).encodeHex(true));
            if ((b & 0xFF) >= 127) {
                sb.append(".toByte()");
            }
            return sb.toString();
        });
    }

    @Override
    public String[] names() {
        return new String[]{"kotlin"};
    }

    @Override
    String getProgrammingLanguageName() {
        return "Kotlin";
    }
}
