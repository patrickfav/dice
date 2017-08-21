package at.favre.tools.dice.encode;

public class Base16LowerCaseEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base16Encoder.Base16LowerCaseEncoder();
    }
}