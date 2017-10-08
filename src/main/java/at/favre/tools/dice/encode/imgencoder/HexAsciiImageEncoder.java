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

package at.favre.tools.dice.encode.imgencoder;

import at.favre.tools.dice.encode.AEncoder;

public class HexAsciiImageEncoder extends AEncoder {
    @Override
    public String encode(byte[] array) {
        return new String(encodeGrayscale(array));
    }

    @Override
    public String encodePadded(byte[] array) {
        return encode(array);
    }

    @Override
    public String[] names() {
        return new String[]{"img"};
    }

    private static final char[] IMG_CHARS =
            {'`', '-', '.', ':', ';', '-', '=', '+', '~', '?', '(', '}', '*', '#', '%', '@'};

    private static char[] encodeGrayscale(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = IMG_CHARS[(0xF0 & data[i]) >>> 4];
            out[j++] = IMG_CHARS[0x0F & data[i]];
        }
        return out;
    }
}
