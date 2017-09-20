package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.Base26Encoder;

public class Base26EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base26Encoder();
    }
}