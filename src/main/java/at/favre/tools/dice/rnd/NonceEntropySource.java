package at.favre.tools.dice.rnd;

import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;

/**
 * Nonce generate as described in SP800-90Ar1. This implementation uses a monotonic sequence number
 * starting with the VM uptime time in millis and the current nano second timestamp
 * <p>
 * See http://nvlpubs.nist.gov/nistpubs/SpecialPublications/NIST.SP.800-90Ar1.pdf Section 8.6.7.
 */
public final class NonceEntropySource implements ExpandableEntropySource {
    private final static byte[] SALT = new byte[]{0x70, 0x6B, (byte) 0xD8, 0x36, 0x15, 0x1E, (byte) 0xC3, 0x79, 0x09, (byte) 0x8D, (byte) 0xEE, 0x75, 0x6F, (byte) 0xED, 0x05, (byte) 0xC8};

    private long sequenceCounter;

    public NonceEntropySource() {
        sequenceCounter = ManagementFactory.getRuntimeMXBean().getStartTime();
    }

    @Override
    public byte[] generateEntropy(int lengthByte) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES * 4);
        buffer.putLong(++sequenceCounter);
        buffer.putLong(System.nanoTime());
        buffer.putLong(System.currentTimeMillis());
        buffer.putLong(ManagementFactory.getRuntimeMXBean().getUptime());
        return HKDF.hkdf(buffer.array(), SALT, SALT, lengthByte);
    }
}
