package at.favre.tools.dice.encode;

import org.apache.commons.codec.binary.BinaryCodec;

import java.util.Arrays;

/**
 * Encodes in to binary representation e.g. <code>010010011</code>
 */
public class BinaryEncoder implements Encoder {

    private final static int GROUP_LENGTH = 8;

    @Override
    public String encode(byte[] array) {
        StringBuilder sb = new StringBuilder();
        char[] outArray = BinaryCodec.toAsciiChars(array);
        for (int i = 0; i < outArray.length; i += GROUP_LENGTH) {
            sb.append(Arrays.copyOfRange(outArray, i, i + GROUP_LENGTH));

            if (i + GROUP_LENGTH < outArray.length) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    @Override
    public String[] names() {
        return new String[]{"binary", "base2", "bin", "bit"};
    }
}
