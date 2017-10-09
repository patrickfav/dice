package at.favre.tools.dice;

import at.favre.tools.dice.rnd.DeterministicRandomBitGenerator;

import java.util.Random;

public class DrbgMock implements DeterministicRandomBitGenerator {
    private Random random = new Random();

    @Override
    public void requestReseed(byte[] additionalInfo) {
        random.setSeed(random.nextLong());
    }

    @Override
    public byte[] nextBytes(int lengthBytes) {
        byte[] b = new byte[lengthBytes];
        nextBytes(b, null);
        return b;
    }

    @Override
    public void nextBytes(byte[] out, byte[] additionalInfo) {
        random.nextBytes(out);
    }
}
