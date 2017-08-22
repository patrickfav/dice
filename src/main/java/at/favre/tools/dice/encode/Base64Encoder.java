package at.favre.tools.dice.encode;

import org.apache.commons.codec.binary.Base64;

/**
 * Encodes in to base64 e.g. <code>NUZDT6c7SOxz0YgRw3JGqc+BKnJM3fuH</code>
 */
public class Base64Encoder extends AByteEncoder {
    private final boolean urlSafe;

    Base64Encoder(boolean urlSafe) {
        this.urlSafe = urlSafe;
    }

    @Override
    public String encode(byte[] array) {
        return new Base64(1, null, urlSafe).encodeAsString(array).replace("=", "");
    }

    @Override
    public String[] names() {
        return new String[]{"base64"};
    }

    @Override
    public String getDescription() {
        return "Base64 is a group of similar binary-to-text encoding schemes that represent binary data in an ASCII string format by translating it into a radix-64 representation. The term Base64 originates from a specific MIME content transfer encoding. This implementation does not use padding.";
    }

    public static class Default extends Base64Encoder {
        public Default() {
            super(false);
        }

        @Override
        public String[] names() {
            return new String[]{"base64"};
        }
    }

    public static class UrlSafe extends Base64Encoder {
        public UrlSafe() {
            super(true);
        }

        @Override
        public String[] names() {
            return new String[]{"base64-url", "base64-safe"};
        }

        @Override
        public String getDescription() {
            return super.getDescription() + ". Uses url safe mode";
        }
    }
}
