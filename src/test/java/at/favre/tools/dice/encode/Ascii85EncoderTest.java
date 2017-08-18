package at.favre.tools.dice.encode;

public class Ascii85EncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Ascii85Encoder();
    }
}