package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.character.AlphaNumericEncoder;

public class AlphaNumericEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new AlphaNumericEncoder();
    }
}