package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.Base32Encoder;

public class Base32EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base32Encoder();
    }
}