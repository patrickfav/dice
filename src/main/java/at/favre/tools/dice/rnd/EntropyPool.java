package at.favre.tools.dice.rnd;

/**
 * Handler of multiple entropy sources, able to generate
 * a uniform entropy output from all added sources.
 */
public interface EntropyPool {

    /**
     * Add new entropy source to the pool
     *
     * @param source
     */
    void add(EntropySource source);

    /**
     * Generate entropy seed from all added sources
     *
     * @param lengthByte length of the output byte array
     * @return byte array of length lengthByte
     */
    byte[] generateSeed(int lengthByte);
}
