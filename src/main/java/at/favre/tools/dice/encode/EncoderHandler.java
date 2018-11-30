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
import at.favre.tools.dice.encode.byteencoder.AByteEncoder;
import at.favre.tools.dice.encode.byteencoder.Base10Encoder;
import at.favre.tools.dice.encode.byteencoder.Base16Encoder;
import at.favre.tools.dice.encode.byteencoder.Base26Encoder;
import at.favre.tools.dice.encode.byteencoder.Base2Encoder;
import at.favre.tools.dice.encode.byteencoder.Base32Encoder;
import at.favre.tools.dice.encode.byteencoder.Base36Encoder;
import at.favre.tools.dice.encode.byteencoder.Base58Encoder;
import at.favre.tools.dice.encode.byteencoder.Base64Encoder;
import at.favre.tools.dice.encode.byteencoder.Base85Encoder;
import at.favre.tools.dice.encode.byteencoder.Base8Encoder;
import at.favre.tools.dice.encode.imgencoder.HexBlockImageEncoder;
import at.favre.tools.dice.encode.languages.CEncoder;
import at.favre.tools.dice.encode.languages.CSharpEncoder;
import at.favre.tools.dice.encode.languages.GoByteArrayEncoder;
import at.favre.tools.dice.encode.languages.JavaByteArrayEncoder;
import at.favre.tools.dice.encode.languages.JsEncoder;
import at.favre.tools.dice.encode.languages.KotlinByteArrayEncoder;
import at.favre.tools.dice.encode.languages.NodeJsEncoder;
import at.favre.tools.dice.encode.languages.PerlEncoder;
import at.favre.tools.dice.encode.languages.PhpEncoder;
import at.favre.tools.dice.encode.languages.Python3Encoder;
import at.favre.tools.dice.encode.languages.RubyEncoder;
import at.favre.tools.dice.encode.languages.RustEncoder;
import at.favre.tools.dice.encode.languages.SwiftEncoder;
import at.favre.tools.dice.encode.textencoder.RawByteEncoder;
import at.favre.tools.dice.encode.textencoder.Utf8Encoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
            new JsEncoder(),
            new PerlEncoder(),
            new PhpEncoder(),
            new Python3Encoder(),
            new RubyEncoder(),
            new RustEncoder(),
            new SwiftEncoder(),
            new HexBlockImageEncoder(),
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
