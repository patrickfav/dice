package at.favre.tools.dice.encode;

public class Base16UpperCaseEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base16Encoder.Base16UpperCaseEncoder();
    }
}