package at.favre.tools.dice.rnd;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Used for weak external entropy source like a user input. This will be combined with a strong
 * {@link SecureRandom} instance which itself seeds with OS entropy pool, therefore mixing the
 * weaker source with a stronger, unpredictable one.
 */
public final class ExternalWeakSeedEntropySource extends SecureRandomEntropySource {

    public ExternalWeakSeedEntropySource(String seed) {
        this(seed.getBytes(StandardCharsets.UTF_8));
    }

    public ExternalWeakSeedEntropySource(byte[] seed) {
        super();
        setSeed(seed);
    }

    @Override
    public String getInformation() {
        return super.getInformation() + " (seeded)";
    }
}
