package at.favre.tools.dice.encode;

import at.favre.tools.dice.util.ByteUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.Assert.assertNotNull;

public abstract class AEncoderTest {
    private byte[] randomBytes8;
    private byte[] randomBytes16;
    private byte[] randomBytes24;
    private byte[] randomBytes32;
    private Encoder encoder;

    @Before
    public void setUp() throws Exception {
        randomBytes8 = ByteUtils.unsecureRandomBytes(8);
        randomBytes16 = ByteUtils.unsecureRandomBytes(16);
        randomBytes24 = ByteUtils.unsecureRandomBytes(24);
        randomBytes32 = ByteUtils.unsecureRandomBytes(32);
        encoder = createInstance();
        System.out.println("\n" + Arrays.toString(encoder.names()));
    }

    @Test
    public void encode() throws Exception {
        testByteArray(new byte[]{1});
        testByteArray(new byte[]{1, 2});
        testByteArray(new byte[]{1, 2, 3});
        testByteArray(new byte[]{1, 2, 3, 4});
        testByteArray(new byte[]{(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        testByteArray(randomBytes8);
        testByteArray(randomBytes16);
        testByteArray(randomBytes24);
        testByteArray(randomBytes32);

        //checkRndDistribution(58);
    }

    private void testByteArray(byte[] bytes) {
        String encoded = encoder.encode(bytes);
        String encodedPadded = encoder.encodePadded(bytes);

        assertNotNull(encoded);
        assertNotNull(encodedPadded);

        System.out.println(encoded);

        if (!encoded.equals(encodedPadded)) {
            System.out.println(encodedPadded);
        }

        check(bytes, encoded);
        check(bytes, encodedPadded);
    }

    abstract void check(byte[] original, String encode);

    abstract Encoder createInstance();

    private static void checkRndDistribution(int max) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < max; i++) {
            map.put(i, 0);
        }
        for (int i = 0; i < 100000; i++) {
            byte[] rnd = new byte[1];
            new SecureRandom().nextBytes(rnd);

            int d = new Random(rnd[0] * 1234).nextInt(max);

            map.put(d, map.get(d) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}