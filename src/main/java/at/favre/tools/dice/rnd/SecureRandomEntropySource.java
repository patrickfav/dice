package at.favre.tools.dice.rnd;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Uses a strong instance of secure random to generate seeds. This will use the OS entropy pool
 * on most JVMs.
 */
public class SecureRandomEntropySource implements ExpandableEntropySource {
    private final SecureRandom secureRandom;

    public SecureRandomEntropySource() {
        try {
            this.secureRandom = SecureRandom.getInstanceStrong();
            /*
             * Right after the SecureRandom constructor, perform a single nextBytes with some small numBytes > 0
             * and disregard the result (even though 0 should do if a Vulcan implemented the spec), then setSeed()
             * with whatever unpredictable data at hand, then use nextBytes.
             *
             * https://crypto.stackexchange.com/a/51222/44838
             */
            secureRandom.nextBytes(new byte[4]);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("could not get strong secure random instace", e);
        }
    }

    @Override
    public byte[] generateEntropy(int length) {
        return secureRandom.generateSeed(length);
    }
}
