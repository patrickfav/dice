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
 * Encodes in node js syntax byte array (e.g. <code>Uint8Array.from([0xD0, 0x3A, 0x4A, 0xEE, 0x64, 0x11]);</code>)
 */
public class JsEncoder extends AProgrammingLanguagesEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "Uint8Array.from([", "]);", ",", b -> "0x" + Bytes.from(b).encodeHex(true));
    }

    @Override
    public String[] names() {
        return new String[]{"js", "javascript"};
    }

    @Override
    String getProgrammingLanguageName() {
        return "Javascript";
    }
}
