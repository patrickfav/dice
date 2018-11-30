package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.languages.JavaByteArrayEncoder;

public class JavaByteArrayEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new JavaByteArrayEncoder();
    }
}
