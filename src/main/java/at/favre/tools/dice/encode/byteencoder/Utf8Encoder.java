package at.favre.tools.dice.encode.byteencoder;

import java.nio.charset.StandardCharsets;

/**
 * Encodes in to utf-8 - this might not be printable
 */
public class Utf8Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return new String(array, StandardCharsets.UTF_8);
    }

    @Override
    public String[] names() {
        return new String[]{"utf8", "utf-8"};
    }

    @Override
    public String getDescription() {
        return "Prints the byte array interpreted as UTF-8 encoded text. Only for testing purpose.";
    }

    @Override
    public boolean urlSafe() {
        return false;
    }

    @Override
    public boolean mayNeedPadding() {
        return false;
    }

    @Override
    public double bitPerByte() {
        return 8;
    }
}
