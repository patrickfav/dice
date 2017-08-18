package at.favre.tools.dice.encode;

public class Ascii94EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Ascii94Encoder();
    }
}