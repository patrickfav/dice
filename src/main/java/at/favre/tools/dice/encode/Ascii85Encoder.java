package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.logic.Ascii85;

/**
 * Encodes in to ascii85 e.g. <code>9jqo^BlbD-BleB1DJ</code>, used by Adobe.
 *
 * See https://github.com/fzakaria/ascii85
 */
public class Ascii85Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return Ascii85.encode(array);
    }

    @Override
    public String[] names() {
        return new String[]{"ascii85", "base85"};
    }
}
