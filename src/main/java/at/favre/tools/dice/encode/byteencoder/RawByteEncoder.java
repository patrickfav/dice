package at.favre.tools.dice.encode.byteencoder;

import java.nio.charset.StandardCharsets;

/**
 * Raw printing of the byte array. This uses ISO_8859_1 string encoding internally because this won't change
 * the raw bytes.
 */
public class RawByteEncoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return new String(array, StandardCharsets.ISO_8859_1);
    }

    @Override
    public String[] names() {
        return new String[]{"raw"};
    }

    @Override
    public String getDescription() {
        return "Prints the raw byte array encoded in ISO_8859_1 which does not change the byte output.";
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

    @Override
    public byte[] asBytes(String encodedString) {
        return encodedString.getBytes(StandardCharsets.ISO_8859_1);
    }
}
