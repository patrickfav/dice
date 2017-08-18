package at.favre.tools.dice.encode;

import java.math.BigInteger;

/**
 * Encodes every byte as a decimal
 */
public class DecimalByteEncoder implements Encoder {

    @Override
    public String encode(byte[] array) {
        return new BigInteger(1, array).toString();
    }

    @Override
    public String[] names() {
        return new String[]{"dec", "decimal"};
    }
}
