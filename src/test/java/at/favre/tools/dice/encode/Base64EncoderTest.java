package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.Base64Encoder;

public class Base64EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base64Encoder.Default();
    }
}
