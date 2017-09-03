package at.favre.tools.dice.encode.languages;

import at.favre.tools.dice.util.ByteUtils;

/**
 * Encodes in Ruby syntax byte array (e.g. <code>array = [ 0xAA, 0x31, 0x3]</code>)
 */
public class RubyEncoder extends AProgrammingLanguagesEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "[", "]", ",", b -> "0x" + ByteUtils.byteToHex(b));
    }

    @Override
    public String[] names() {
        return new String[]{"ruby"};
    }

    @Override
    String getProgrammingLanguageName() {
        return "Ruby";
    }
}
