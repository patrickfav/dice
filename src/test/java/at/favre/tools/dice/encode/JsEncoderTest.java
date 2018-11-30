package at.favre.tools.dice.encode;

import at.favre.tools.dice.encode.languages.JsEncoder;

public class JsEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new JsEncoder();
    }
}
