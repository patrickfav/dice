package at.favre.tools.dice.encode.byteencoder;

/**
 * Encodes in to base36 like used by tinyurl e.g. <code>5k9wsvhu60h35c7otmmktlah</code>
 */
public class Base36Encoder extends AByteRadixEncoder {

    @Override
    int getRadix() {
        return 36;
    }

    @Override
    public String[] names() {
        return new String[]{"base36"};
    }

    @Override
    public String getDescription() {
        return "Base36 translating into a radix-36 (aka Hexatrigesimal) representation.";
    }

    @Override
    public boolean urlSafe() {
        return true;
    }

    @Override
    public boolean mayNeedPadding() {
        return true;
    }

    @Override
    public double bitPerByte() {
        return 5.16993;
    }
}
