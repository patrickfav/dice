package at.favre.tools.dice.encode;

import at.favre.tools.dice.util.ByteUtils;

/**
 * Encodes in node js syntax byte array (e.g. <code>new Buffer([0x4, 0xFF, 0x01, 0x20, 0x0]);</code>)
 */
public class NodeJsEncoder extends AEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "new Buffer([", "]);", ",", b -> "0x" + ByteUtils.byteToHex(b));
    }

    @Override
    public String[] names() {
        return new String[]{"node", "nodejs", "js", "javascript"};
    }
}
