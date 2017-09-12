package at.favre.tools.dice.rnd;

import at.favre.tools.dice.util.ByteUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultEntropyPoolTest {

    private EntropyPool entropyPool;

    @Before
    public void setup() {
        entropyPool = new DefaultEntropyPool();
        entropyPool.add(new ExternalSeedEntropySource("7192837aadasdlkj"));
        entropyPool.add(new SecureRandomEntropySource());
        entropyPool.add(new FingerprintEntropySource());
    }

    @Test
    public void generateSeed() throws Exception {
        Set<byte[]> pastSeeds = new HashSet<>();
        for (int i = 8; i < 64; i += 8) {
            byte[] seed = entropyPool.generateSeed(i);
            assertTrue(seed.length == i);
            assertFalse(pastSeeds.contains(seed));
            pastSeeds.add(seed);

            System.out.println(ByteUtils.bytesToHex(seed));
        }
    }
}