package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.languages.Python3Encoder;

public class Python3EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Python3Encoder();
    }
}