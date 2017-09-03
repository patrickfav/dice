package at.favre.tools.dice.encode.languages;

import at.favre.tools.dice.util.ByteUtils;

/**
 * Encodes in Perl syntax byte array (e.g. <code>$a2_bytes = pack 0xAA, 0x31, 0x3;</code>)
 */
public class PerlEncoder extends AProgrammingLanguagesEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "pack ", ";", ",", b -> "0x" + ByteUtils.byteToHex(b));
    }

    @Override
    public String[] names() {
        return new String[]{"perl"};
    }

    @Override
    String getProgrammingLanguageName() {
        return "Perl";
    }
}
