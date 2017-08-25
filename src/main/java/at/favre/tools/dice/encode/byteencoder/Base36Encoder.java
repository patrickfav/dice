package at.favre.tools.dice.encode.byteencoder;

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
        return "Base36 translating into a radix-36 (aka Hexatrigesimal) representation.";
    }

    @Override
    public double spaceEfficiency() {
        return 0.65;
    }

    @Override
    public boolean urlSafe() {
        return true;
    }

    @Override
    public boolean mayNeedPadding() {
        return true;
    }
}
