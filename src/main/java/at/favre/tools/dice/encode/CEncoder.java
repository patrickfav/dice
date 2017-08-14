package at.favre.tools.dice.encode;

import at.favre.tools.dice.util.ByteUtils;

/**
 * Encodes in c syntax byte array (e.g. <code>{ 0x00, 0x11, 0x22 };</code>)
 */
public class CEncoder extends AEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "{", "};", ",", b -> "0x" + ByteUtils.byteToHex(b));
    }

    @Override
    public String[] names() {
        return new String[]{"c", "c++"};
    }
}
