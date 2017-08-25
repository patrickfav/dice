package at.favre.tools.dice.encode.byteencoder;


import com.github.fzakaria.ascii85.Ascii85;

/**
 * Encodes in to ascii85 e.g. <code>9jqo^BlbD-BleB1DJ</code>, used by Adobe.
 *
 * See https://github.com/fzakaria/ascii85
 */
public class Base85Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return Ascii85.encode(array);
    }

    @Override
    public String[] names() {
        return new String[]{"ascii85", "base85"};
    }

    @Override
    public String getDescription() {
        return "Base85 uses an 85 character ASCII alphabet to encode. It's main use is with the PDF format and GIT.";
    }

    @Override
    public double spaceEfficiency() {
        return 0.85;
    }

    @Override
    public boolean urlSafe() {
        return true;
    }

    @Override
    public boolean mayNeedPadding() {
        return true;
    }
}
