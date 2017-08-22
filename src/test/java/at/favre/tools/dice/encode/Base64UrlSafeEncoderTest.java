package at.favre.tools.dice.encode;

public class Base64UrlSafeEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new Base64Encoder.UrlSafe();
    }
}