package at.favre.tools.dice.encode;

import java.nio.charset.StandardCharsets;

public abstract class AEncoder implements Encoder {
    @Override
    public String getDescription() {
        return "<TODO>";
    }

    @Override
    public byte[] asBytes(String encodedString) {
        return encodedString.getBytes(StandardCharsets.UTF_8);
    }
}
