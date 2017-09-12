package at.favre.tools.dice.rnd;

public interface EntropyPool {

    void add(EntropySource source);

    byte[] generateSeed(int length);
}
