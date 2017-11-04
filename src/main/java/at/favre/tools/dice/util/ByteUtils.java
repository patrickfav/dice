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

package at.favre.tools.dice.util;

import at.favre.lib.bytes.Bytes;
import at.favre.lib.bytes.BytesTransformer;

import java.util.zip.CRC32;

public final class ByteUtils {
    private ByteUtils() {
    }

    /**
     * Will append CRC check
     */
    public static class Crc32AppenderTransformer implements BytesTransformer {
        /**
         * Will append 4 bytes of CRC32 checksum after the original
         * byte array, making it 4 bytes longer
         *
         * @param original
         * @return original (+) crc32
         */
        @Override
        public byte[] transform(byte[] original, boolean inPlace) {
            CRC32 crc32 = new CRC32();
            crc32.update(original);
            return Bytes.from(Bytes.wrap(original), Bytes.from(crc32.getValue()).copy(3, 4)).array();
        }
    }
}
