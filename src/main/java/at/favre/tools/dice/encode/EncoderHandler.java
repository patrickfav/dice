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

package at.favre.tools.dice.encode;

import at.favre.lib.bytes.Bytes;
import at.favre.tools.dice.encode.byteencoder.*;
import at.favre.tools.dice.encode.imgencoder.HexAsciiImageEncoder;
import at.favre.tools.dice.encode.languages.*;
import at.favre.tools.dice.encode.textencoder.RawByteEncoder;
import at.favre.tools.dice.encode.textencoder.Utf8Encoder;

import java.util.*;

public final class EncoderHandler {

    static final List<Encoder> ENCODERS = Collections.unmodifiableList(Arrays.asList(
            new Base2Encoder(),
            new Base8Encoder(),
            new Base10Encoder(),
            new Base16Encoder.Base16LowerCaseEncoder(),
            new Base16Encoder.Base16UpperCaseEncoder(),
            new Base26Encoder(),
            new Base32Encoder(),
            new Base36Encoder(),
            new Base58Encoder.BitcoinStyle(),
            new Base64Encoder.Default(),
            new Base64Encoder.UrlSafe(),
            new Base85Encoder(),
            new CEncoder(),
            new CSharpEncoder(),
            new JavaByteArrayEncoder(),
            new GoByteArrayEncoder(),
            new KotlinByteArrayEncoder(),
            new NodeJsEncoder(),
            new PerlEncoder(),
            new PhpEncoder(),
            new Python3Encoder(),
            new RubyEncoder(),
            new RustEncoder(),
            new SwiftEncoder(),
            new HexAsciiImageEncoder(),
            new RawByteEncoder(),
            new Utf8Encoder()
    ));

    private final byte[] exampleBytes = Bytes.random(7).array();

    public List<Encoder> load() {
        Set<String> modes = new HashSet<>();

        for (Encoder encoder : ENCODERS) {
            for (String name : encoder.names()) {
                if (modes.contains(name)) {
                    throw new IllegalStateException(name + " is already defined in another encoder");
                }
                modes.add(name);
            }
        }

        return ENCODERS;
    }

    public Encoder findByName(String name) {
        if (name != null) {
            for (Encoder encoder : ENCODERS) {
                if (Arrays.asList(encoder.names()).contains(name)) {
                    return encoder;
                }
            }
        }
        return null;
    }

    public String getFullSupportedEncodingList() {
        StringBuilder sb = new StringBuilder();

        for (Encoder encoder : ENCODERS) {
            sb.append("[");
            for (String name : encoder.names()) {
                sb.append(name).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()).append("]").append("\n");
            sb.append("\t").append(encoder.getDescription()).append("\n");

            if (encoder instanceof AByteEncoder) {
                sb.append("\tSpace Efficiency: ").append(String.format(Locale.US, "%.1f", ((AByteEncoder) encoder).spaceEfficiency() * 100)).append("%");
                sb.append("; Url-Safe: ").append(((AByteEncoder) encoder).urlSafe());
                sb.append("; Needs padding: ").append(((AByteEncoder) encoder).mayNeedPadding()).append("\n");
            }
            sb.append("\tExample: ").append(encoder.encode(exampleBytes)).append("\n\n");
        }
        return sb.toString();
    }

    public String getSupportedEncodingList() {
        StringBuilder sb = new StringBuilder();

        for (Encoder encoder : ENCODERS) {
            sb.append(encoder.names()[0]).append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }


}
