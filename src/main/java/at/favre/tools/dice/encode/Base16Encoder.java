package at.favre.tools.dice.encode;

import org.apache.commons.codec.binary.Hex;

/**
 * Encodes in to base16 (ie. hex) e.g. <code>6e33a8f4c9f69e91</code>
 */
public class Base16Encoder implements Encoder {
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
        return new String[]{"base16", "hex"};
    }
}
