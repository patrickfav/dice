package at.favre.tools.dice.rnd;

public class SecureRandomEntropySourceTest extends AEntropySourceTest {
    @Override
    EntropySource getEntropySource() {
        return new SecureRandomEntropySource();
    }
}