package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.languages.PerlEncoder;

public class PerlEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new PerlEncoder();
    }
}