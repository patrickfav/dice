package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.Base8Encoder;

public class Base8EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base8Encoder();
    }
}
