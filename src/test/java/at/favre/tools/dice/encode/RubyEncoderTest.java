package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.languages.RubyEncoder;

public class RubyEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new RubyEncoder();
    }
}