package at.favre.tools.dice.encode;

import java.util.*;

public class Loader {

    public static final List<Encoder> ENCODERS = Collections.unmodifiableList(Arrays.asList(
            new AlphaNumericEncoder(),
            new Ascii85Encoder(),
            new Ascii94Encoder(),
            new Base16Encoder(),
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
}
