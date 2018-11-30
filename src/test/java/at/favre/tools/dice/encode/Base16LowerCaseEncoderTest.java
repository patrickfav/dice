package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.Base16Encoder;

public class Base16LowerCaseEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base16Encoder.Base16LowerCaseEncoder();
    }
}
