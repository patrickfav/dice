package at.favre.tools.dice.rnd;

import at.favre.tools.dice.util.ByteUtils;

import java.nio.charset.StandardCharsets;

public class ExternalSeedEntropySource implements EntropySource {
    private final static int INTERNAL_SEED_LENGTH = 32;
    private final static byte[] SALT = new byte[]{0x29, 0x05, 0x27, 0x2B, (byte) 0xD7, 0x56, (byte) 0x84, 0x27, (byte) 0xD6, (byte) 0xE1, 0x62, 0x4B, (byte) 0xBD, (byte) 0xC9, 0x62, (byte) 0x80};

    private byte[] internalSeed;

    public ExternalSeedEntropySource(String seed) {
        this(seed.getBytes(StandardCharsets.UTF_8));
    }

    public ExternalSeedEntropySource(byte[] seed) {
        regenerateInternalSeed(seed);
    }

    private void regenerateInternalSeed(byte[] seed) {
        internalSeed = HKDF.hkdf(seed, SALT, SALT, INTERNAL_SEED_LENGTH);
    }

    @Override
    public byte[] generateEntropy() {
        byte[] copy = ByteUtils.copy(internalSeed);
        regenerateInternalSeed(internalSeed);
        return copy;
    }
}
