package at.favre.tools.dice.encode.languages;

import at.favre.tools.dice.util.ByteUtils;

/**
 * Encodes in java syntax byte array (e.g. <code>new byte[]{0x3A,...}</code>)
 */
public class JavaByteArrayEncoder extends AProgrammingLanguagesEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "new byte[]{", "};", ",", b -> {
            StringBuilder sb = new StringBuilder();
            if ((b & 0xFF) >= 127) {
                sb.append("(byte) ");
            }
            sb.append("0x").append(ByteUtils.byteToHex(b));
            return sb.toString();
        });
    }

    @Override
    public String[] names() {
        return new String[]{"java"};
    }
}
