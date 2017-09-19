package at.favre.tools.dice.encode.byteencoder;

import at.favre.tools.dice.encode.Encoder;

import java.math.BigInteger;

public abstract class AByteEncoder implements Encoder {
    @Override
    public String encodePadded(byte[] array) {
        return encode(array);
    }

    @Override
    public String getDescription() {
        return "<TODO>";
    }

    public double spaceEfficiency() {
        return bitPerByte() / 8.0;
    }

    public abstract boolean urlSafe();

    public abstract boolean mayNeedPadding();

    public abstract double bitPerByte();

    int maxLength(byte[] data, int radix) {
        return BigInteger.valueOf(2).pow(BigInteger.valueOf(data.length).multiply(BigInteger.valueOf(8)).intValue()).toString(radix).length();
    }
}
