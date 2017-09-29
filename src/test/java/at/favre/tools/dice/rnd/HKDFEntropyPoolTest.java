package at.favre.tools.dice.rnd;

import at.favre.tools.dice.util.ByteUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class HKDFEntropyPoolTest {

    private EntropyPool entropyPool;

    @Before
    public void setup() {
        entropyPool = new HKDFEntropyPool();
        entropyPool.add(new ExternalStrongSeedEntropySource("7192837aadasdlkj"));
        entropyPool.add(new SecureRandomEntropySource());
        entropyPool.add(new PersonalizationSource());
    }

    @Test
    public void generateSeed() throws Exception {
        assertNotNull(entropyPool.getInformation());

        Set<byte[]> pastSeeds = new HashSet<>();
        for (int i = 8; i < 64; i += 8) {
            byte[] seed = entropyPool.generateEntropy(i);
            assertTrue(seed.length == i);
            assertFalse(pastSeeds.contains(seed));
            pastSeeds.add(seed);

            System.out.println(ByteUtils.bytesToHex(seed));
        }
    }
}