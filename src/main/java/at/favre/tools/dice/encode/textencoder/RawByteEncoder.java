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

import at.favre.tools.dice.encode.AEncoder;
import at.favre.tools.dice.encode.DefaultEncoderFormat;
import at.favre.tools.dice.encode.EncoderFormat;

import java.nio.charset.StandardCharsets;

/**
 * Raw printing of the byte array. This uses ISO_8859_1 string encoding internally because this won't change
 * the raw bytes.
 */
public class RawByteEncoder extends AEncoder {

    @Override
    public String encode(byte[] array) {
        return new String(array, StandardCharsets.ISO_8859_1);
    }

    @Override
    public String encodePadded(byte[] array) {
        return encode(array);
    }

    @Override
    public String[] names() {
        return new String[]{"raw"};
    }

    @Override
    public String getDescription() {
        return "Prints the raw byte array encoded in ISO_8859_1 which does not change the byte output. Most useful with file output.";
    }

    @Override
    public EncoderFormat getEncoderFormat() {
        return new DefaultEncoderFormat(" ", "", System.lineSeparator(), "", System.lineSeparator(), "", StandardCharsets.ISO_8859_1, Integer.MAX_VALUE);
    }
}
