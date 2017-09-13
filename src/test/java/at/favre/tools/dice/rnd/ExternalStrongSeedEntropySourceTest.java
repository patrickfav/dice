package at.favre.tools.dice.rnd;

public class ExternalStrongSeedEntropySourceTest extends AEntropySourceTest {
    @Override
    EntropySource getEntropySource() {
        return new ExternalStrongSeedEntropySource("kas7p2ejkash78d2lasdh8");
    }
}