package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.languages.CSharpEncoder;

public class CSharpEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new CSharpEncoder();
    }
}