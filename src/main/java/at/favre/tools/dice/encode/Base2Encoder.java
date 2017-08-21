package at.favre.tools.dice.encode;

import org.apache.commons.codec.binary.BinaryCodec;

import java.util.Arrays;

/**
 * Encodes in to binary representation e.g. <code>010010011</code>
 */
public class Base2Encoder extends AByteEncoder {

    private final static int GROUP_LENGTH = 8;

    @Override
    public String encode(byte[] array) {
        StringBuilder sb = new StringBuilder();
        inPlaceReverse(array); //reverse array to print big endian
        char[] outArray = BinaryCodec.toAsciiChars(array);
        for (int i = 0; i < outArray.length; i += GROUP_LENGTH) {
            sb.append(Arrays.copyOfRange(outArray, i, i + GROUP_LENGTH));

            if (i + GROUP_LENGTH < outArray.length) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private void inPlaceReverse(byte[] validData) {
        for (int i = 0; i < validData.length / 2; i++) {
            byte temp = validData[i];
            validData[i] = validData[validData.length - i - 1];
            validData[validData.length - i - 1] = temp;
        }
    }

    @Override
    public String[] names() {
        return new String[]{"binary", "base2", "bin", "bit"};
    }

    @Override
    public String getDescription() {
        return "A simple binary representation with '0' and '1' divided into 8 bit groups. This is a very inefficient representation therfore only for special use cases";
    }
}
