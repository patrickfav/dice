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

import java.util.HashMap;
import java.util.Map;

/**
 * Encodes every byte as a character from A-Z e.g. <code>CRMALTVKWFEMPZ</code>
 */
public class Base26Encoder extends AByteRadixEncoder {
    private final Map<Character, Character> charMap = new HashMap<>();

    public Base26Encoder() {
        charMap.put('0', 'A');
        charMap.put('1', 'B');
        charMap.put('2', 'C');
        charMap.put('3', 'D');
        charMap.put('4', 'E');
        charMap.put('5', 'F');
        charMap.put('6', 'G');
        charMap.put('7', 'H');
        charMap.put('8', 'I');
        charMap.put('9', 'J');
        charMap.put('A', 'K');
        charMap.put('B', 'L');
        charMap.put('C', 'M');
        charMap.put('D', 'N');
        charMap.put('E', 'O');
        charMap.put('F', 'P');
        charMap.put('G', 'Q');
        charMap.put('H', 'R');
        charMap.put('I', 'S');
        charMap.put('J', 'T');
        charMap.put('K', 'U');
        charMap.put('L', 'V');
        charMap.put('M', 'W');
        charMap.put('N', 'X');
        charMap.put('O', 'Y');
        charMap.put('P', 'Z');
    }

    @Override
    int getRadix() {
        return 26;
    }

    @Override
    public String[] names() {
        return new String[]{"base26"};
    }

    @Override
    public String encode(byte[] array) {
        String encodedRadix36 = super.encode(array);
        char[] output = encodedRadix36.toUpperCase().toCharArray();
        for (int i = 0; i < output.length; i++) {
            output[i] = charMap.get(output[i]);
        }
        return new String(output);
    }

    @Override
    public String getDescription() {
        return "Base26 uses the twenty-six letters A-Z.";
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
        return 4.70044;
    }
}
