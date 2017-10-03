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

import org.apache.commons.codec.binary.Base64;

/**
 * Encodes in to base64 e.g. <code>NUZDT6c7SOxz0YgRw3JGqc+BKnJM3fuH</code>
 */
public class Base64Encoder extends AByteEncoder {
    private final boolean urlSafe;

    Base64Encoder(boolean urlSafe) {
        this.urlSafe = urlSafe;
    }

    @Override
    public String encode(byte[] array) {
        return encodePadded(array).replace("=", "");
    }

    @Override
    public String encodePadded(byte[] array) {
        return new Base64(1, null, urlSafe).encodeAsString(array);
    }

    @Override
    public String[] names() {
        return new String[]{"base64"};
    }

    @Override
    public String getDescription() {
        return "Base64 represent binary data in an ASCII string format by translating it into a radix-64 representation.";
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
        return 6;
    }

    public static class Default extends Base64Encoder {
        public Default() {
            super(false);
        }

        @Override
        public String[] names() {
            return new String[]{"base64"};
        }
    }

    public static class UrlSafe extends Base64Encoder {
        public UrlSafe() {
            super(true);
        }

        @Override
        public String[] names() {
            return new String[]{"base64-url", "base64-safe"};
        }

        @Override
        public String getDescription() {
            return super.getDescription() + " Uses url safe mode";
        }

        @Override
        public boolean urlSafe() {
            return true;
        }
    }
}
