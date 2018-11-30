package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.Base2Encoder;

public class Base2EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base2Encoder();
    }
}
