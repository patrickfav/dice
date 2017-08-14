package at.favre.tools.dice.encode;

public class Base16EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base16Encoder(true);
    }
}