package at.favre.tools.dice.rnd;

/**
 * Interface for a entropy source with arbitrary length
 */
public interface ExpandableEntropySource {

    /**
     * Generates a bit string of entropy. Successive calls must not return
     * the same data.
     *
     * @param lengthByte the output length
     * @return a (pseudo) random byte array of
     */
    byte[] generateEntropy(int lengthByte);
}
