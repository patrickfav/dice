package at.favre.tools.dice.encode;

public class Base36EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base36Encoder();
    }
}