package at.favre.tools.dice.encode;

public class AsciiEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new AsciiEncoder();
    }
}