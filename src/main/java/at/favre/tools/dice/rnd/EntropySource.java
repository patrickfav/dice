package at.favre.tools.dice.rnd;

/**
 * Interface for a entropy source
 */
public interface EntropySource {

    /**
     * Generates a bit string of entropy. Successive calls must not return
     * the same data. The length of the output is depended on the implementation
     * but must be greater than 16 byte.
     *
     * @return a (pseudo) random byte array of
     */
    byte[] generateEntropy();
}
