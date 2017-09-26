package at.favre.tools.dice.rnd;

import at.favre.tools.dice.util.ByteUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Used with external seeds that are estimated to be strong. The strong seed will
 * used in quasi directly (only HKDF after each call will be preformed additional to
 * adding an monotonic counter, to generate different outputs each call)
 */
public final class ExternalStrongSeedEntropySource implements ExpandableEntropySource {
    private final static int INTERNAL_SEED_LENGTH = 64;

    private final static byte[] SALT = new byte[]{0x29, 0x05, 0x27, 0x2B, (byte) 0xD7, 0x56, (byte) 0x84, 0x27, (byte) 0xD6, (byte) 0xE1, 0x62, 0x4B, (byte) 0xBD, (byte) 0xC9, 0x62, (byte) 0x80};

    private byte[] internalSeed;
    private int counter = 0;

    public ExternalStrongSeedEntropySource(String seed) {
        this(seed.getBytes(StandardCharsets.UTF_8));
    }

    public ExternalStrongSeedEntropySource(byte[] seed) {
        regenerateInternalSeed(seed);
    }

    private void regenerateInternalSeed(byte[] seed) {
        internalSeed = HKDF.hkdf(ByteUtils.concatAll(seed, ByteBuffer.allocate(Integer.BYTES).putInt(counter++).array()), SALT, SALT, INTERNAL_SEED_LENGTH);
    }

    @Override
    public byte[] generateEntropy(int length) {
        byte[] out = HKDF.hkdf(internalSeed, SALT, SALT, length);
        regenerateInternalSeed(internalSeed);
        return out;
    }
}
