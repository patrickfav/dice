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

package at.favre.tools.dice.encode.textencoder;

import at.favre.tools.dice.encode.byteencoder.AByteEncoder;

import java.nio.charset.StandardCharsets;

/**
 * Encodes in to utf-8 - this might not be printable
 */
public class Utf8Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return new String(array, StandardCharsets.UTF_8);
    }

    @Override
    public String[] names() {
        return new String[]{"utf8", "utf-8"};
    }

    @Override
    public String getDescription() {
        return "Prints the byte array interpreted as UTF-8 encoded text. Only for testing purpose.";
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
        return 8;
    }
}
