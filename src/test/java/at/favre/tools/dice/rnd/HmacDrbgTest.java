package at.favre.tools.dice.rnd;

import at.favre.tools.dice.util.ByteUtils;
import at.favre.tools.dice.util.Entropy;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HmacDrbgTest {

    private DeterministicRandomBitGenerator hmacDrbg;

    @Before
    public void setUp() throws Exception {
        hmacDrbg = new HmacDrbg(length -> new SecureRandom().generateSeed(length), length -> new SecureRandom().generateSeed(length), length -> new SecureRandom().generateSeed(length));
    }

    @Test
    public void nextBytesHundredTimesShouldNotRepeat() throws Exception {
        Set<byte[]> pastSeeds = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            byte[] out = hmacDrbg.nextBytes(16);
            assertTrue(out.length == 16);
            assertTrue(new Entropy<>(ByteUtils.toList(out)).entropy() > 3);
            assertFalse(pastSeeds.contains(out));
            pastSeeds.add(out);
            System.out.println(ByteUtils.bytesToHex(out));
        }
    }

}