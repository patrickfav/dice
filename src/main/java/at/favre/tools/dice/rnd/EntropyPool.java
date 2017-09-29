package at.favre.tools.dice.rnd;

/**
 * Handler of multiple entropy sources, able to generate
 * a uniform entropy output from all added sources.
 */
public interface EntropyPool extends ExpandableEntropySource {

    /**
     * Add new entropy source to the pool
     *
     * @param source
     */
    void add(ExpandableEntropySource source);

    /**
     * Information string about the pool's source
     *
     * @return readable info string
     */
    String getInformation();
}
