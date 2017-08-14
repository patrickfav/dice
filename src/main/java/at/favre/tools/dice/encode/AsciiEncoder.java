package at.favre.tools.dice.encode;

/**
 * Encodes every byte with full range of ascii chars
 */
public class AsciiEncoder extends AlphabetEncoder {
    public AsciiEncoder() {
        super("!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[~]^_`abcdefghijklmnopqrstuvwxyz{|}");
    }

    @Override
    public String[] names() {
        return new String[]{"ascii"};
    }
}
