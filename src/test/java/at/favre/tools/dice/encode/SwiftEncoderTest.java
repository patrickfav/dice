package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.languages.SwiftEncoder;

public class SwiftEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new SwiftEncoder();
    }
}