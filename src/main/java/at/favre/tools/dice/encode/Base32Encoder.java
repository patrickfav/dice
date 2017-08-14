package at.favre.tools.dice.encode;

import org.apache.commons.codec.binary.Base32;

/**
 * Encodes in to base32 e.g. <code>NHSMJ5VH6P5QRAT4EXAP2BDZ5Q</code>
 */
public class Base32Encoder implements Encoder {

    @Override
    public String encode(byte[] array) {
        return new Base32((byte) '=').encodeToString(array).replace("=", "");
    }

    @Override
    public String[] names() {
        return new String[]{"base32"};
    }
}
