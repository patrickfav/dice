package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.UUEncoder;

public class UUEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new UUEncoder();
    }
}