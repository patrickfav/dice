package at.favre.tools.dice.encode;

public class UUEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new UUEncoder();
    }
}