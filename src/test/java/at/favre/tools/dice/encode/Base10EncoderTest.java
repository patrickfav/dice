package at.favre.tools.dice.encode;

public class Base10EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base10Encoder();
    }
}