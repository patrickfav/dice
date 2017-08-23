package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.Utf8Encoder;

public class Utf8EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Utf8Encoder();
    }
}