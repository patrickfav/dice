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

/**
 * Encodes in to octal e.g. <code>171143734066371107705</code>
 */
public class Base8Encoder extends AByteRadixEncoder {

    @Override
    public String[] names() {
        return new String[]{"octal", "oct", "base8"};
    }

    @Override
    public String getDescription() {
        return "The octal numeral system, is the base-8 number system, and uses the digits 0 to 7.";
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
        return 3;
    }

    @Override
    int getRadix() {
        return 8;
    }
}
