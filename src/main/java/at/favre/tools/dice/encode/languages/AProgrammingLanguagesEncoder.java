package at.favre.tools.dice.encode.languages;

import at.favre.tools.dice.encode.AEncoder;

public abstract class AProgrammingLanguagesEncoder extends AEncoder {
    String encodeInternal(byte[] array, String prefix, String postfix, String sep, ByteEncoder byteEncoder) {
        StringBuilder sb = new StringBuilder(prefix);
        for (byte anArray : array) {
            sb.append(byteEncoder.encodeByte(anArray)).append(sep).append(" ");
        }

        sb.replace(sb.length() - 2, sb.length(), postfix);
        return sb.toString();
    }

    interface ByteEncoder {
        String encodeByte(byte b);
    }

    @Override
    public String getDescription() {
        return "Syntax for initializing an array of type byte in " + getProgrammingLanguageName() + ".";
    }

    abstract String getProgrammingLanguageName();
}
