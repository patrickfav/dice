package at.favre.tools.dice.encode.languages;

import at.favre.tools.dice.util.ByteUtils;

/**
 * Encodes in Rust syntax byte array (e.g. <code>[u8; 4] = [0x1, 0x2, 0x3, 0x4];</code>)
 */
public class RustEncoder extends AProgrammingLanguagesEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "[u8; " + array.length + "] = [", "];", ",", b -> "0x" + ByteUtils.byteToHex(b).toLowerCase());
    }

    @Override
    public String[] names() {
        return new String[]{"rust"};
    }

    @Override
    String getProgrammingLanguageName() {
        return "Rust";
    }
}
