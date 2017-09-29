package at.favre.tools.dice.rnd;

import at.favre.tools.dice.util.ByteUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public abstract class AEntropySourceTest {
    abstract ExpandableEntropySource getExpandableEntropySource();

    @Test
    public void generateExpandableEntropyTest() throws Exception {
        ExpandableEntropySource entropySource = getExpandableEntropySource();
        if (entropySource == null) {
            return;
        }

        assertNotNull(entropySource.getInformation());

        Set<byte[]> pastSeeds = new HashSet<>();
        for (int i = 8; i < 64; i += 8) {
            byte[] seed = entropySource.generateEntropy(i);
            assertTrue(seed.length == i);
            assertFalse(pastSeeds.contains(seed));
            pastSeeds.add(seed);

            System.out.println(ByteUtils.bytesToHex(seed));
        }
    }
}
