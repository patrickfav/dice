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
 * Encodes every byte as a decimal
 */
public class Base10Encoder extends AByteRadixEncoder {

    @Override
    int getRadix() {
        return 10;
    }

    @Override
    public String[] names() {
        return new String[]{"dec", "decimal", "base10"};
    }

    @Override
    public String getDescription() {
        return "Decimal positive sign-magnitude representation representation in big-endian byte-order.";
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
        return 3.32193;
    }
}
