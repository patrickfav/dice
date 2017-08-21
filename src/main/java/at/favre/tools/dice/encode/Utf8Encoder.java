package at.favre.tools.dice.encode;

import java.nio.charset.StandardCharsets;

/**
 * Encodes in to utf-8 - this might not be printable
 */
public class Utf8Encoder extends AByteEncoder {

    @Override
    public String encode(byte[] array) {
        return new String(array, StandardCharsets.UTF_8);
    }

    @Override
    public String[] names() {
        return new String[]{"utf8"};
    }

    @Override
    public String getDescription() {
        return "UTF-8 is a compromise character encoding that can be as compact as ASCII (if the file is just plain English text) but can also contain any unicode characters (with some increase in file size).";
    }
}
