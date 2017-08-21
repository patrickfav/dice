package at.favre.tools.dice.encode;

import java.math.BigInteger;

/**
 * Encodes in to octal e.g. <code>171143734066371107705</code>
 */
public class Base8Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return new BigInteger(1, array).toString(8);
    }

    @Override
    public String[] names() {
        return new String[]{"octal", "oct", "base8"};
    }

    @Override
    public String getDescription() {
        return "The octal numeral system, or oct for short, is the base-8 number system, and uses the digits 0 to 7. Octal numerals can be made from binary numerals by grouping consecutive binary digits into groups of three (starting from the right)";
    }
}
