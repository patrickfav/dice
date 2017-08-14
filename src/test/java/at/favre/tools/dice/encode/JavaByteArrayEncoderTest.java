package at.favre.tools.dice.encode;

public class JavaByteArrayEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new JavaByteArrayEncoder();
    }
}