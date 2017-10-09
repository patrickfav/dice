package at.favre.tools.dice.rnd;

import at.favre.tools.dice.rnd.entropy.FixedEntropySource;
import at.favre.tools.dice.util.ByteUtils;
import at.favre.tools.dice.util.Entropy;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.*;

import static org.junit.Assert.*;

public class HmacDrbgTest extends AHmacDrbgNistTestVectorsTest {

    private DeterministicRandomBitGenerator hmacDrbgSha512;
    private DeterministicRandomBitGenerator hmacDrbgSha256;
    private DeterministicRandomBitGenerator hmacDrbgSha1;

    private ExpandableEntropySource mockExpandableEntropySource = new ExpandableEntropySource() {
        @Override
        public byte[] generateEntropy(int lengthByte) {
            return new SecureRandom().generateSeed(lengthByte);
        }

        @Override
        public String getInformation() {
            return "info";
        }
    };

    @Before
    public void setUp() throws Exception {
        hmacDrbgSha512 = new HmacDrbg(new DrbgParameter(MacFactory.Default.hmacSha512(),
                mockExpandableEntropySource,
                mockExpandableEntropySource,
                new byte[16]
        ));
        hmacDrbgSha256 = new HmacDrbg(new DrbgParameter(MacFactory.Default.hmacSha256(),
                mockExpandableEntropySource,
                mockExpandableEntropySource,
                new byte[16]
        ));
        hmacDrbgSha1 = new HmacDrbg(new DrbgParameter(MacFactory.Default.hmacSha1(),
                mockExpandableEntropySource,
                mockExpandableEntropySource,
                new byte[16]
        ));
    }

    @Test
    public void testMultipleHmacAndShouldNotRepeat() throws Exception {
        List<DeterministicRandomBitGenerator> generators = new ArrayList<>(3);
        generators.add(hmacDrbgSha512);
        generators.add(hmacDrbgSha256);
        generators.add(hmacDrbgSha1);

        Set<byte[]> pastSeeds = new HashSet<>();
        Random r = new Random();

        for (DeterministicRandomBitGenerator drbg : generators) {
            for (int i = 0; i < 5000; i++) {
                int nextLen = r.nextInt(32) + 4;
                byte[] out = drbg.nextBytes(nextLen);
                assertTrue(out.length == nextLen);
                assertFalse(pastSeeds.contains(out));
                pastSeeds.add(out);
            }

            for (int i = 0; i < 500; i++) {
                int nextLen = 16;
                byte[] out = drbg.nextBytes(nextLen);
                assertTrue(out.length == nextLen);
                assertTrue(new Entropy<>(ByteUtils.toList(out)).entropy() > 3);
                assertFalse(pastSeeds.contains(out));
                pastSeeds.add(out);
            }
        }
    }

    @Test
    public void testHmacDrbgZeroLengthOutput() {
        byte[] entropy =
                hex("8ca4a964e1ff68753db86753d09222e09b888b500be46f2a3830afa9172a1d6d");
        byte[] nonce = hex("a59394e0af764e2f21cf751f623ffa6c");

        HmacDrbg drbg = new HmacDrbg(new DrbgParameter(MacFactory.Default.hmacSha256(),
                new FixedEntropySource(entropy),
                new FixedEntropySource(nonce), null));
        byte[] out = drbg.nextBytes(0);
        assertArrayEquals(new byte[0], out);
    }

    @Test
    public void testShouldAutoReseed() {
        byte[] entropy = hex("8ca4a964e1ff68753db86753d09222e09b888b500be46f2a3830afa9172a1d6d");
        byte[] nonce = hex("a59394e0af764e2f21cf751f623ffa6c");

        HmacDrbg drbg = new HmacDrbg(new DrbgParameter(MacFactory.Default.hmacSha256(),
                new FixedEntropySource(entropy),
                new FixedEntropySource(nonce), null));
        byte[] out = drbg.nextBytes(0);
        assertArrayEquals(new byte[0], out);
    }
}