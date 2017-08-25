package at.favre.tools.dice.encode.languages;

import at.favre.tools.dice.util.ByteUtils;

/**
 * Encodes in swift syntax byte array (e.g. <code>var foo:[UInt8] = [0x00, 0x11, 0x22 ]</code>)
 */
public class SwiftEncoder extends AProgrammingLanguagesEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "[UInt8] = [", "]", ",", b -> "0x" + ByteUtils.byteToHex(b));
    }

    @Override
    public String[] names() {
        return new String[]{"swift"};
    }

    @Override
    String getProgrammingLanguageName() {
        return "Swift";
    }
}
