package at.favre.tools.dice.rnd;

public class ExternalSeedEntropySourceTest extends AEntropySourceTest {
    @Override
    EntropySource getEntropySource() {
        return new ExternalSeedEntropySource("kas7p2ejkash78d2lasdh8");
    }
}