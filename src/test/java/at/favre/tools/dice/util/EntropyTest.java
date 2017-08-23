package at.favre.tools.dice.util;

import at.favre.tools.dice.encode.byteencoder.Base16Encoder;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EntropyTest {

    @Test
    public void entropy() throws Exception {
        testEntropyIntern(new byte[]{0, 0, 0, 0, 0, 0, 0}, 0);
        testEntropyIntern(new byte[]{0, 0, 0, 0, 0, 0, 1}, 0);
        testEntropyIntern(new byte[]{1, 0, 0, 1, 0, 1, 0}, 0.9);
        testEntropyIntern(new byte[]{0, 2, 0, 1, 0, 1, 3}, 0.9);
        testEntropyIntern(new byte[]{17, 122, -98, 66, 9, 54, -12, 70}, 2);
        testEntropyIntern(ByteUtils.unsecureRandomBytes(8), 2);
        testEntropyIntern(ByteUtils.unsecureRandomBytes(16), 3);
        testEntropyIntern(ByteUtils.unsecureRandomBytes(32), 3);
    }

    private void testEntropyIntern(byte[] bytes, double biggerThanEntropy) {
        Entropy<Byte> entropy = new Entropy<>(ByteUtils.toList(bytes));
        double entropyMeasure = entropy.entropy();
        double perplexity = entropy.perplexity();

        System.out.println(new Base16Encoder.Base16LowerCaseEncoder().encode(bytes) + " - entropy: " + entropyMeasure + " - perplexity: " + perplexity);
        assertTrue(entropyMeasure >= biggerThanEntropy);
    }
}