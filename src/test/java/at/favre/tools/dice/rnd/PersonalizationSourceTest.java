package at.favre.tools.dice.rnd;

import at.favre.tools.dice.rnd.entropy.PersonalizationSource;

public class PersonalizationSourceTest extends AEntropySourceTest {

    @Override
    ExpandableEntropySource getExpandableEntropySource() {
        return new PersonalizationSource();
    }
}