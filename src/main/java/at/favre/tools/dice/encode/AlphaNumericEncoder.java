package at.favre.tools.dice.encode;

/**
 * Encodes every byte as a decimal
 */
public class AlphaNumericEncoder extends AlphabetEncoder {
    public AlphaNumericEncoder() {
        super("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
    }

    @Override
    public String[] names() {
        return new String[]{"alpha-numeric"};
    }
}
