package at.favre.tools.dice.rnd;

public class FingerprintEntropySourceTest extends AEntropySourceTest {
    @Override
    EntropySource getEntropySource() {
        return new FingerprintEntropySource();
    }
}