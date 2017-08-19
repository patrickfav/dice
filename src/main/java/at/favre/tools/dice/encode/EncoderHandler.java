package at.favre.tools.dice.encode;

import java.util.*;

public class EncoderHandler {

    public static final List<Encoder> ENCODERS = Collections.unmodifiableList(Arrays.asList(
            new AlphaNumericEncoder(),
            new Ascii85Encoder(),
            new Ascii94Encoder(),
            new Base16Encoder.Base16LowerCaseEncoder(),
            new Base16Encoder.Base16UpperCaseEncoder(),
            new Base32Encoder(),
            new Base64Encoder(),
            new BinaryEncoder(),
            new CEncoder(),
            new CSharpEncoder(),
            new DecimalByteEncoder(),
            new JavaByteArrayEncoder(),
            new KotlinByteArrayEncoder(),
            new NodeJsEncoder(),
            new NumericEncoder(),
            new PhpEncoder(),
            new Python3Encoder(),
            new SwiftEncoder()
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
        final byte[] exampleBytes = new byte[]{(byte) 0xC6, 0x1A, 0x66, 0x3F, 0x3E, 0x1A, (byte) 0x8B, (byte) 0xAB};
        StringBuilder sb = new StringBuilder();

        for (Encoder encoder : ENCODERS) {
            sb.append("[");
            for (String name : encoder.names()) {
                sb.append(name).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()).append("]").append("\n");
            sb.append("\t").append(encoder.getDescription()).append("\n");
            sb.append("\tExample: ").append(encoder.encode(exampleBytes)).append("\n");
        }
        return sb.toString();
    }
}
