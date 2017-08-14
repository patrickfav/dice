package at.favre.tools.dice.encode;

import at.favre.tools.dice.util.ByteUtils;

/**
 * Encodes in python syntax byte array (e.g. <code>bytes([0x13, 0x00,... ])</code>)
 */
public class Python3Encoder extends AEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "bytes([", "])", ",", b -> "0x" + ByteUtils.byteToHex(b));
    }

    @Override
    public String[] names() {
        return new String[]{"python3"};
    }
}
