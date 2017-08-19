package at.favre.tools.dice.encode;

import at.favre.tools.dice.ui.Arg;
import org.apache.commons.codec.binary.Hex;

/**
 * Encodes in to base16 (ie. hex) e.g. <code>6e33a8f4c9f69e91</code>
 */
public abstract class Base16Encoder extends AByteEncoder {
    private boolean lowerCase;

    public Base16Encoder(boolean lowerCase) {
        this.lowerCase = lowerCase;
    }

    @Override
    public String encode(byte[] array) {
        return String.valueOf(Hex.encodeHex(array, lowerCase));
    }

    @Override
    public String getDescription() {
        return "Hex or Base16 encoding (lowercase) e.g 6e33a8f4c";
    }

    public static class Base16LowerCaseEncoder extends Base16Encoder {
        public Base16LowerCaseEncoder() {
            super(true);
        }

        @Override
        public String[] names() {
            return new String[]{"base16", Arg.DEFAULT_ENCODING};
        }
    }

    public static class Base16UpperCaseEncoder extends Base16Encoder {
        public Base16UpperCaseEncoder() {
            super(false);
        }

        @Override
        public String[] names() {
            return new String[]{"BASE16", "HEX"};
        }

        @Override
        public String getDescription() {
            return "Hex or Base16 encoding (uppercase) e.g 6E33A8F4C";
        }
    }
}
