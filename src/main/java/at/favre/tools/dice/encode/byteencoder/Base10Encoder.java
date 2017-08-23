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
    public String[] names() {
        return new String[]{"dec", "decimal", "base10"};
    }

    @Override
    public String getDescription() {
        return "Decimal positive sign-magnitude representation representation in big-endian byte-order.";
    }
}
