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
 * Encodes in to base36 like used by tinyurl e.g. <code>5k9wsvhu60h35c7otmmktlah</code>
 */
public class Base36Encoder extends AByteRadixEncoder {

    @Override
    int getRadix() {
        return 36;
    }

    @Override
    public String[] names() {
        return new String[]{"base36"};
    }

    @Override
    public String getDescription() {
        return "Base36 translating into a radix-36 (aka Hexatrigesimal) representation.";
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
        return 5.16993;
    }
}
