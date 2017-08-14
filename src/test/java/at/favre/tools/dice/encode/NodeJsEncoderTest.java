package at.favre.tools.dice.encode;

public class NodeJsEncoderTest extends AEncoderTest {
    @Override
    void check(byte[] original, String encode) {

    }

    @Override
    Encoder createInstance() {
        return new NodeJsEncoder();
    }
}