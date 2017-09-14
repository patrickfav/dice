package at.favre.tools.dice.rnd;

public class PersonalizationSourceTest extends AEntropySourceTest {

    @Override
    ExpandableEntropySource getExpandableEntropySource() {
        return new PersonalizationSource();
    }
}