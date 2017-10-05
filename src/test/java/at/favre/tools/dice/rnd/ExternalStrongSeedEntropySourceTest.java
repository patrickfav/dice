package at.favre.tools.dice.rnd;

import at.favre.tools.dice.rnd.entropy.ExternalStrongSeedEntropySource;

public class ExternalStrongSeedEntropySourceTest extends AEntropySourceTest {
    @Override
    ExpandableEntropySource getExpandableEntropySource() {
        return new ExternalStrongSeedEntropySource("kas7p2ejkash78d2lasdh8");
    }
}