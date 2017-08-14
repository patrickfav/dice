package at.favre.tools.dice.encode;

/**
 * Encodes every byte as a decimal
 */
public class DecimalEncoder extends AlphabetEncoder {
    public DecimalEncoder() {
        super("0123456789");
    }

    @Override
    public String[] names() {
        return new String[]{"dec", "decimal"};
    }
}
