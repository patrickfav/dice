package at.favre.tools.dice.rnd;

public class SecureRandomEntropySourceTest extends AEntropySourceTest {

    @Override
    ExpandableEntropySource getExpandableEntropySource() {
        return new SecureRandomEntropySource();
    }
}