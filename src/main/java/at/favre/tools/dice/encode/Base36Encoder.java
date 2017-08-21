package at.favre.tools.dice.encode;

import java.math.BigInteger;

/**
 * Encodes in to base36 like used by tinyurl e.g. <code>5k9wsvhu60h35c7otmmktlah</code>
 */
public class Base36Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return new BigInteger(1, array).toString(36);
    }

    @Override
    public String[] names() {
        return new String[]{"base36"};
    }

    @Override
    public String getDescription() {
        return "Base36 is a binary-to-text encoding scheme that represents binary data in an ASCII string format by translating it into a radix-36 (aka Hexatrigesimal) representation. The choice of 36 is convenient in that the digits can be represented using the Arabic numerals 0–9 and the Latin letters A–Z.";
    }
}
