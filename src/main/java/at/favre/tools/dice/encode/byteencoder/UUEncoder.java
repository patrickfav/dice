package at.favre.tools.dice.encode.byteencoder;


/**
 * Encodes in the same format as unix to unix (uuencode) e.g. <code>(H%(]5H6I(P ! </code>
 * <p>
 * See https://en.wikipedia.org/wiki/Uuencoding
 */
public class UUEncoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return new sun.misc.UUEncoder().encode(array);
    }

    @Override
    public String[] names() {
        return new String[]{"uuencode", "uuencoding"};
    }

    @Override
    public String getDescription() {
        return "The name 'uuencoding' is derived from 'Unix-to-Unix encoding', i.e. the idea of using a safe encoding to transfer Unix files from one Unix system to another Unix system but without guarantee that the intervening links would all be Unix systems. Since an email message might be forwarded through or to computers with different character sets or through transports which are not 8-bit clean, or handled by programs that are not 8-bit clean";
    }
}
