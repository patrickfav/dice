package at.favre.tools.dice.encode.byteencoder;

/**
 * Encodes in to octal e.g. <code>171143734066371107705</code>
 */
public class Base8Encoder extends AByteRadixEncoder {

    @Override
    public String[] names() {
        return new String[]{"octal", "oct", "base8"};
    }

    @Override
    public String getDescription() {
        return "The octal numeral system, is the base-8 number system, and uses the digits 0 to 7.";
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
        return 3;
    }

    @Override
    int getRadix() {
        return 8;
    }
}
