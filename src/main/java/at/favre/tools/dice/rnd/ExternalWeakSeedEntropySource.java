package at.favre.tools.dice.rnd;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Used for weak external entropy source like a user input. This will be combined with a strong
 * {@link SecureRandom} instance which itself seeds with OS entropy pool, therefore mixing the
 * weaker source with a stronger, unpredictable one.
 */
public class ExternalWeakSeedEntropySource implements ExpandableEntropySource {
    private final SecureRandom secureRandom;

    public ExternalWeakSeedEntropySource(String seed) {
        this(seed.getBytes(StandardCharsets.UTF_8));
    }

    public ExternalWeakSeedEntropySource(byte[] seed) {
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
            secureRandom.setSeed(seed);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("could not get strong secure random instance", e);
        }
    }

    @Override
    public byte[] generateEntropy(int length) {
        return secureRandom.generateSeed(length);
    }
}
