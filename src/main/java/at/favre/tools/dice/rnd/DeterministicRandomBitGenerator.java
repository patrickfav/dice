package at.favre.tools.dice.rnd;

/**
 * A Deterministic Random Bit Generator as defined by NIST, will output
 * pseudo random data depending on a given seed.
 */
public interface DeterministicRandomBitGenerator {

    /**
     * Get the next pseudo random data
     *
     * @param lengthBytes output length in byte
     * @return the random data
     */
    byte[] nextBytes(int lengthBytes);

    /**
     * Get the next pseudo random data.
     *
     * @param out fills this byte array with data
     */
    void nextBytes(byte[] out);
}
