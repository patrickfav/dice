package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.*;
import at.favre.tools.dice.encode.character.AlphaNumericEncoder;
import at.favre.tools.dice.encode.character.Ascii94Encoder;
import at.favre.tools.dice.encode.character.NumericEncoder;
import at.favre.tools.dice.encode.languages.*;

import java.util.*;

public class EncoderHandler {

    private static final List<Encoder> ENCODERS = Collections.unmodifiableList(Arrays.asList(
            new AlphaNumericEncoder(),
            new Ascii94Encoder(),
            new Base2Encoder(),
            new Base8Encoder(),
            new Base10Encoder(),
            new Base16Encoder.Base16LowerCaseEncoder(),
            new Base16Encoder.Base16UpperCaseEncoder(),
            new Base32Encoder(),
            new Base36Encoder(),
            new Base64Encoder.Default(),
            new Base64Encoder.UrlSafe(),
            new Base85Encoder(),
            new CEncoder(),
            new CSharpEncoder(),
            new JavaByteArrayEncoder(),
            new GoByteArrayEncoder(),
            new KotlinByteArrayEncoder(),
            new NodeJsEncoder(),
            new NumericEncoder(),
            new PhpEncoder(),
            new Python3Encoder(),
            new SwiftEncoder(),
            new Utf8Encoder(),
            new UUEncoder()
    ));

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

    public String returnRegistryInfo() {
        final byte[] exampleBytes = new byte[]{0x00, 0x23, (byte) 0xA9, (byte) 0x85, 0x56, 0x3D, 0x52, (byte) 0xA0};
        StringBuilder sb = new StringBuilder();

        for (Encoder encoder : ENCODERS) {
            sb.append("[");
            for (String name : encoder.names()) {
                sb.append(name).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()).append("]").append("\n");
            sb.append("\t").append(encoder.getDescription()).append("\n");
            if (encoder instanceof AByteEncoder) {
                sb.append("\tSpace Efficiency: ").append(((AByteEncoder) encoder).spaceEfficiency() * 100).append("%");
                sb.append("; Url-Safe: ").append(((AByteEncoder) encoder).urlSafe());
                sb.append("; Needs padding: ").append(((AByteEncoder) encoder).mayNeedPadding()).append("\n");
            }
            sb.append("\tExample: ").append(encoder.encode(exampleBytes)).append("\n\n");
        }
        return sb.toString();
    }
}
