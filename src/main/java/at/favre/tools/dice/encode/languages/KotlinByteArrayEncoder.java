package at.favre.tools.dice.encode.languages;

import at.favre.tools.dice.util.ByteUtils;

/**
 * Encodes in kotlin syntax byte array (e.g. <code>byteArrayOf(0x3A,...)</code>)
 */
public class KotlinByteArrayEncoder extends AProgrammingLanguagesEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "byteArrayOf(", ")", ",", b -> {
            StringBuilder sb = new StringBuilder();
            sb.append("0x").append(ByteUtils.byteToHex(b));
            if ((b & 0xFF) >= 127) {
                sb.append(".toByte()");
            }
            return sb.toString();
        });
    }

    @Override
    public String[] names() {
        return new String[]{"kotlin"};
    }

    @Override
    String getProgrammingLanguageName() {
        return "Kotlin";
    }
}
