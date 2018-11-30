package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.Base85Encoder;

public class Base85EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base85Encoder();
    }
}
