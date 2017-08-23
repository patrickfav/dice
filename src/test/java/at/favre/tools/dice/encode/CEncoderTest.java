package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.languages.CEncoder;

public class CEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new CEncoder();
    }
}