package at.favre.tools.dice.rnd;

import at.favre.tools.dice.rnd.entropy.SecureRandomEntropySource;

public class SecureRandomEntropySourceTest extends AEntropySourceTest {

    @Override
    ExpandableEntropySource getExpandableEntropySource() {
        return new SecureRandomEntropySource();
    }
}