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

import com.github.fzakaria.ascii85.Ascii85;

/**
 * Encodes in to ascii85 e.g. <code>9jqo^BlbD-BleB1DJ</code>, used by Adobe.
 * <p>
 * See https://github.com/fzakaria/ascii85
 */
public class Base85Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return Ascii85.encode(array);
    }

    @Override
    public String[] names() {
        return new String[]{"base85", "ascii85"};
    }

    @Override
    public String getDescription() {
        return "Base85 uses an 85 character ASCII alphabet to encode. It's main use is with the PDF format and GIT.";
    }

    @Override
    public boolean urlSafe() {
        return false;
    }

    @Override
    public boolean mayNeedPadding() {
        return true;
    }

    @Override
    public double bitPerByte() {
        return 6.40939;
    }
}
