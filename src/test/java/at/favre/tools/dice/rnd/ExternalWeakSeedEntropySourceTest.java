package at.favre.tools.dice.rnd;

import at.favre.tools.dice.rnd.entropy.ExternalWeakSeedEntropySource;

public class ExternalWeakSeedEntropySourceTest extends AEntropySourceTest {
    @Override
    ExpandableEntropySource getExpandableEntropySource() {
        return new ExternalWeakSeedEntropySource("1234");
    }
}