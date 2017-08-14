package at.favre.tools.dice.encode;

public abstract class AEncoder implements Encoder {

    public String encodeInternal(byte[] array, String prefix, String postfix, String sep, ByteEncoder byteEncoder) {
        StringBuilder sb = new StringBuilder(prefix);
        for (int i = 0; i < array.length; i++) {
            sb.append(byteEncoder.encodeByte(array[i])).append(sep).append(" ");
        }

        sb.replace(sb.length() - 2, sb.length(), postfix);
        return sb.toString();
    }

    interface ByteEncoder {
        String encodeByte(byte b);
    }
}
