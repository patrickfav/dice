package at.favre.tools.dice.encode.character;

/**
 * Encodes every byte as a decimal
 */
public class NumericEncoder extends AlphabetEncoder {
    public NumericEncoder() {
        super("0123456789");
    }

    @Override
    public String[] names() {
        return new String[]{"num", "numeric"};
    }
}
