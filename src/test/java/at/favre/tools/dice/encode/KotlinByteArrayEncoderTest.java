package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.languages.KotlinByteArrayEncoder;

public class KotlinByteArrayEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new KotlinByteArrayEncoder();
    }
}
