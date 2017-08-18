package at.favre.tools.dice.encode;

import at.favre.tools.dice.ui.Arg;
import org.apache.commons.codec.binary.Hex;

/**
 * Encodes in to base16 (ie. hex) e.g. <code>6e33a8f4c9f69e91</code>
 */
public class Base16Encoder extends AByteEncoder {
    private boolean lowerCase;

    public Base16Encoder() {
        this(true);
    }

    public Base16Encoder(boolean lowerCase) {
        this.lowerCase = lowerCase;
    }

    @Override
    public String encode(byte[] array) {
        return String.valueOf(Hex.encodeHex(array, lowerCase));
    }

    @Override
    public String[] names() {
        return new String[]{"base16", Arg.DEFAULT_ENCODING};
    }

    @Override
    public String getDescription() {
        return "Hex or Base16 encoding (lowercase) e.g 6e33a8f4c";
    }
}
