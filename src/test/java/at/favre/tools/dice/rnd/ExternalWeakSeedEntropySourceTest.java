package at.favre.tools.dice.rnd;

public class ExternalWeakSeedEntropySourceTest extends AEntropySourceTest {
    @Override
    ExpandableEntropySource getExpandableEntropySource() {
        return new ExternalWeakSeedEntropySource("1234");
    }
}