package at.favre.tools.dice.rnd;

import at.favre.lib.crypto.HKDF;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * Used for weak external entropy source like a user input. The seed will be extracted with HKDF
 * and combined with a{@link SecureRandom} instance which itself seeds with OS entropy pool,
 * therefore mixing the weaker source with a stronger, unpredictable one.
 */
public final class ExternalWeakSeedEntropySource extends SecureRandomEntropySource {

    private static final byte[] SALT = new byte[]{0x6A, (byte) 0xA0, (byte) 0x92, (byte) 0xEA, 0x51, (byte) 0xEB, (byte) 0xB4, (byte) 0xDC, 0x22, (byte) 0x82, (byte) 0xED, 0x29, 0x53, 0x4B, (byte) 0x88, (byte) 0x8C, 0x75, 0x0E, 0x75, 0x59, 0x78, 0x6D, (byte) 0xEC, (byte) 0xDD, 0x5E, (byte) 0xBA, 0x3D, (byte) 0xD6, (byte) 0xC3, 0x70, (byte) 0xB4, (byte) 0x84};


    public ExternalWeakSeedEntropySource(String seed) {
        this(seed.getBytes(StandardCharsets.UTF_8));
    }

    public ExternalWeakSeedEntropySource(byte[] seed) {
        super();
        setSeed(HKDF.fromHmacSha256().extract(seed, SALT));
    }

    @Override
    public String getInformation() {
        return super.getInformation() + " (seeded)";
    }
}
