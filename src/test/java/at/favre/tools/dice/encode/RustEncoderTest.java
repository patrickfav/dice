package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.languages.RustEncoder;

public class RustEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new RustEncoder();
    }
}
