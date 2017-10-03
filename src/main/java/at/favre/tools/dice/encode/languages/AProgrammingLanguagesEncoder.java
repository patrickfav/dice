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

import at.favre.tools.dice.encode.AEncoder;

public abstract class AProgrammingLanguagesEncoder extends AEncoder {
    String encodeInternal(byte[] array, String prefix, String postfix, String sep, ByteEncoder byteEncoder) {
        StringBuilder sb = new StringBuilder(prefix);
        for (byte anArray : array) {
            sb.append(byteEncoder.encodeByte(anArray)).append(sep).append(" ");
        }

        sb.replace(sb.length() - 2, sb.length(), postfix);
        return sb.toString();
    }

    interface ByteEncoder {
        String encodeByte(byte b);
    }

    @Override
    public String encodePadded(byte[] array) {
        return encode(array);
    }

    @Override
    public String getDescription() {
        return "Syntax for initializing an array of type byte in " + getProgrammingLanguageName() + ".";
    }

    abstract String getProgrammingLanguageName();
}
