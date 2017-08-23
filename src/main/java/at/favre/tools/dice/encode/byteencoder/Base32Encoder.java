package at.favre.tools.dice.encode.byteencoder;

import org.apache.commons.codec.binary.Base32;

/**
 * Encodes in to base32 e.g. <code>NHSMJ5VH6P5QRAT4EXAP2BDZ5Q</code>
 */
public class Base32Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return new Base32((byte) '=').encodeToString(array).replace("=", "");
    }

    @Override
    public String[] names() {
        return new String[]{"base32"};
    }

    @Override
    public String getDescription() {
        return "Base32 is one of several base 32 transfer encodings using a 32-character subset of the twenty-six letters A-Z and ten digits 0-9. Uses the alphabet defined in RFC 4648. Base32 representation takes roughly 20% more space than Base64. This implementation does not use padding.";
    }
}
