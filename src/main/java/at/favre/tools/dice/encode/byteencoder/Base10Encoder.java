package at.favre.tools.dice.encode.byteencoder;

import java.math.BigInteger;

/**
 * Encodes every byte as a decimal
 */
public class Base10Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return new BigInteger(1, array).toString(10);
    }

    @Override
    public String encodePadded(byte[] array) {
        return String.format("%" + String.valueOf((int) Math.pow(2, array.length * 8)).length() + "s", encode(array)).replace(' ', '0');
    }

    public String encod(byte[] array) {
        return new BigInteger(1, array).toString(10);
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
    public double spaceEfficiency() {
        return 0.33;
    }

    @Override
    public boolean urlSafe() {
        return true;
    }

    @Override
    public boolean mayNeedPadding() {
        return false;
    }
}
