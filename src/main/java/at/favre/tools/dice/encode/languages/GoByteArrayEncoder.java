package at.favre.tools.dice.encode.languages;

import at.favre.tools.dice.util.ByteUtils;

/**
 * Encodes in go syntax byte array (e.g. <code>[...]byte = {0, 21,...}</code>)
 */
public class GoByteArrayEncoder extends AProgrammingLanguagesEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "[...]byte = {", "}", ",", (byte b) -> "0x" + ByteUtils.byteToHex(b));
    }

    @Override
    public String[] names() {
        return new String[]{"go"};
    }

    @Override
    String getProgrammingLanguageName() {
        return "Go";
    }
}
