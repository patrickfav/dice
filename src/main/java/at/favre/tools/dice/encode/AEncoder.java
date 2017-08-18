package at.favre.tools.dice.encode;

abstract class AEncoder implements Encoder {

    String encodeInternal(byte[] array, String prefix, String postfix, String sep, ByteEncoder byteEncoder) {
        StringBuilder sb = new StringBuilder(prefix);
        for (int i = 0; i < array.length; i++) {
            sb.append(byteEncoder.encodeByte(array[i])).append(sep).append(" ");
        }

        sb.replace(sb.length() - 2, sb.length(), postfix);
        return sb.toString();
    }

    @Override
    public String getDescription() {
        return "<TODO>";
    }

    interface ByteEncoder {
        String encodeByte(byte b);
    }
}
