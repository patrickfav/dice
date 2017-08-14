package at.favre.tools.dice.encode;

public class Python3EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Python3Encoder();
    }
}