package at.favre.tools.dice.encode.character;

/**
 * Encodes every byte with full range of ascii chars
 */
public class Ascii94Encoder extends AlphabetEncoder {
    public Ascii94Encoder() {
        super("!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[~]^_`abcdefghijklmnopqrstuvwxyz{|}");
    }

    @Override
    public String[] names() {
        return new String[]{"ascii", "ascii94", "base94"};
    }
}
