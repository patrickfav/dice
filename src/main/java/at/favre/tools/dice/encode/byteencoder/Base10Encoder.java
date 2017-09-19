package at.favre.tools.dice.encode.byteencoder;

/**
 * Encodes every byte as a decimal
 */
public class Base10Encoder extends AByteRadixEncoder {

    @Override
    int getRadix() {
        return 10;
    }

    @Override
    public String[] names() {
        return new String[]{"dec", "decimal", "base10"};
    }

    @Override
    public String getDescription() {
        return "Decimal positive sign-magnitude representation representation in big-endian byte-order.";
    }

    @Override
    public boolean urlSafe() {
        return true;
    }

    @Override
    public boolean mayNeedPadding() {
        return true;
    }

    @Override
    public double bitPerByte() {
        return 3.32193;
    }
}
