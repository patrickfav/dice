package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.languages.GoByteArrayEncoder;

public class GoEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new GoByteArrayEncoder();
    }
}