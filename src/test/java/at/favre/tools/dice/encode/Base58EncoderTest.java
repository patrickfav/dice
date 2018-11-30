package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.byteencoder.Base58Encoder;

public class Base58EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base58Encoder.BitcoinStyle();
    }
}
