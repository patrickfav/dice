package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.Base36Encoder;

public class Base36EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base36Encoder();
    }
}