package at.favre.tools.dice.rnd;

public interface DeterministicRandomBitGenerator {
    byte[] nextBytes(int length);

    void nextBytes(byte[] out);
}
