package at.favre.tools.dice.rnd;

import at.favre.lib.crypto.HKDF;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

/**
 * Default implementation of entropy pool using KDF method HKDF to generate uniformly distributed entropy
 * bit strings.
 */
public final class HKDFEntropyPool implements EntropyPool {
    private final static int DEFAULT_INTERNAL_SEED_LENGTH = 32;
    private final static byte[] SALT = new byte[]{(byte) 0xDD, (byte) 0x8A, 0x7E, (byte) 0xEF, 0x09, 0x06, (byte) 0xEC, 0x09, 0x3F, (byte) 0xDB, 0x1B, 0x16, (byte) 0xD5, 0x20, 0x65, (byte) 0xAE};

    private final List<ExpandableEntropySource> entropySourceList = new LinkedList<>();

    private final int sourceSeedLengthBytes;


    public HKDFEntropyPool() {
        this(DEFAULT_INTERNAL_SEED_LENGTH);
    }

    /**
     * Create a new entropy pool
     *
     * @param sourceSeedLengthBytes this many bytes will be requested by each entropy source; will internally at least take 16 bytes
     */
    public HKDFEntropyPool(int sourceSeedLengthBytes) {
        this.sourceSeedLengthBytes = Math.max(16, sourceSeedLengthBytes);
    }

    @Override
    public void add(ExpandableEntropySource source) {
        entropySourceList.add(source);
    }

    @Override
    public String getInformation() {
        StringBuilder sb = new StringBuilder();
        entropySourceList.forEach(e -> sb.append(e.getInformation()).append('\n'));
        return sb.toString();
    }

    @Override
    public byte[] generateEntropy(int lengthByte) {
        if (entropySourceList.isEmpty()) {
            throw new IllegalStateException("entropy pool must not be empty - add entropy sources first");
        }

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        entropySourceList.forEach(source -> {
            try {
                bos.write(source.generateEntropy(sourceSeedLengthBytes));
            } catch (IOException e) {
                throw new IllegalStateException("could not generate seed in pool", e);
            }
        });
        return HKDF.fromHmacSha512().extractAndExpand(SALT, bos.toByteArray(), this.getClass().getName().getBytes(StandardCharsets.UTF_8), lengthByte);
    }
}
