package at.favre.tools.dice.rnd;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class DefaultEntropyPool implements EntropyPool, HmacDrbg.EntropySource {
    private final static byte[] SALT = new byte[]{(byte) 0xDD, (byte) 0x8A, 0x7E, (byte) 0xEF, 0x09, 0x06, (byte) 0xEC, 0x09, 0x3F, (byte) 0xDB, 0x1B, 0x16, (byte) 0xD5, 0x20, 0x65, (byte) 0xAE};

    private final List<EntropySource> entropySourceList = new LinkedList<>();

    @Override
    public void add(EntropySource source) {
        entropySourceList.add(source);
    }

    @Override
    public byte[] generateSeed(int length) {
        if (entropySourceList.isEmpty()) {
            throw new IllegalStateException("entropy pool must not be empty - add entropy sources first");
        }

        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        entropySourceList.forEach(source -> {
            try {
                bos.write(source.generateEntropy());
            } catch (IOException e) {
                throw new IllegalStateException("could not generate seed in pool", e);
            }
        });
        return HKDF.hkdf(bos.toByteArray(), SALT, SALT, length);
    }

    @Override
    public byte[] generateEntropyInput(int lengthByte) {
        return generateSeed(lengthByte);
    }
}
