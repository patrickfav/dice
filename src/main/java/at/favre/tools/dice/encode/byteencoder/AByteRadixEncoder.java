package at.favre.tools.dice.encode.byteencoder;

import java.math.BigInteger;

public abstract class AByteRadixEncoder extends AByteEncoder {

    abstract int getRadix();

    @Override
    public String encode(byte[] array) {
        return new BigInteger(1, array).toString(getRadix());
    }

    @Override
    public String encodePadded(byte[] array) {
        return String.format("%" + maxLength(array, getRadix()) + "s", encode(array)).replace(' ', '0');
    }


}
