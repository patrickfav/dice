package at.favre.tools.dice.rnd;

import at.favre.tools.dice.util.ByteUtils;
import at.favre.tools.dice.util.Entropy;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public abstract class AEntropySourceTest {
    abstract EntropySource getEntropySource();

    @Test
    public void generateEntropyTest() throws Exception {
        EntropySource entropySource = getEntropySource();
        byte[] entropy = entropySource.generateEntropy();

        assertNotNull(entropy);
        assertTrue(entropy.length >= 16);

        byte[] entropy2 = entropySource.generateEntropy();
        assertFalse(Arrays.equals(entropy, entropy2));

        assertTrue(new Entropy<>(ByteUtils.toList(entropy)).entropy() > 3);
        assertTrue(new Entropy<>(ByteUtils.toList(entropy2)).entropy() > 3);

        System.out.println(ByteUtils.bytesToHex(entropy));
        System.out.println(ByteUtils.bytesToHex(entropy2));
    }
}
