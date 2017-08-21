package at.favre.tools.dice.encode;

public class Utf8EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Utf8Encoder();
    }
}