package at.favre.tools.dice.encode;

import org.apache.commons.codec.binary.Base64;

/**
 * Encodes in to base64 e.g. <code>NUZDT6c7SOxz0YgRw3JGqc+BKnJM3fuH</code>
 */
public class Base64Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return new Base64().encodeAsString(array).replace("=", "");
    }

    @Override
    public String[] names() {
        return new String[]{"base64"};
    }

    @Override
    public String getDescription() {
        return "Base64 encoding without padding e.g NUZDT6c7SOxz0YgRw3JGqc+BKnJM3fuH";
    }
}
