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

import at.favre.tools.dice.util.ByteUtils;

/**
 * Encodes in Rust syntax byte array (e.g. <code>[u8; 4] = [0x1, 0x2, 0x3, 0x4];</code>)
 */
public class RustEncoder extends AProgrammingLanguagesEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "[u8; " + array.length + "] = [", "];", ",", b -> "0x" + ByteUtils.byteToHex(b).toLowerCase());
    }

    @Override
    public String[] names() {
        return new String[]{"rust"};
    }

    @Override
    String getProgrammingLanguageName() {
        return "Rust";
    }
}
