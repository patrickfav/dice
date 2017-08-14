package at.favre.tools.dice.encode;

/**
 * Encodes in php syntax byte array (e.g. <code>array(1,10,6,67);</code>)
 */
public class PhpEncoder extends AEncoder {
    @Override
    public String encode(byte[] array) {
        return encodeInternal(array, "array(", ");", ",", b -> String.valueOf(b & 0xFF));
    }

    @Override
    public String[] names() {
        return new String[]{"php"};
    }
}
