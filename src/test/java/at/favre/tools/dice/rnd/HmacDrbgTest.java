package at.favre.tools.dice.rnd;

import at.favre.tools.dice.util.ByteUtils;
import at.favre.tools.dice.util.Entropy;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HmacDrbgTest {

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

    /*

    [SHA-1]
    [PredictionResistance = False]
    [EntropyInputLen = 128]
    [NonceLen = 64]
    [PersonalizationStringLen = 0]
    [AdditionalInputLen = 0]
    [ReturnedBitsLen = 640]

    COUNT = 0
    EntropyInput = e91b63309e93d1d08e30e8d556906875
    Nonce = f59747c468b0d0da
    PersonalizationString =
    ** INSTANTIATE:
        V   = 7ea45af5f8fcba082fa40bcbea2748dfe7e09f6a
        Key = be3976a33f77e0155b7ca84a5732d44f319e5f3a
    AdditionalInput =
    ** GENERATE (FIRST CALL):
        V   = 0e28fe04dd16482f8e4b048675318adcd5e6e6cf
        Key = 764d4f1fb7b04624bcb14642acb24d70eff3c0c8
    AdditionalInput =
    ReturnedBits = b7928f9503a417110788f9d0c2585f8aee6fb73b220a626b3ab9825b7a9facc79723d7e1ba9255e40e65c249b6082a7bc5e3f129d3d8f69b04ed1183419d6c4f2a13b304d2c5743f41c8b0ee73225347
         */

    @Test
    public void nistTestVector() throws Exception {
        DeterministicRandomBitGenerator drbg = new HmacDrbg(new DrbgParameter(
                MacFactory.Default.hmacSha1(),
                new FixedEntropySource(hex("e91b63309e93d1d08e30e8d556906875")),
                new FixedEntropySource(hex("f59747c468b0d0da")),
                new byte[0]
        ));
        //assertArrayEquals(hex("b7928f9503a417110788f9d0c2585f8aee6fb73b220a626b3ab9825b7a9facc79723d7e1ba9255e40e65c249b6082a7bc5e3f129d3d8f69b04ed1183419d6c4f2a13b304d2c5743f41c8b0ee73225347"), drbg.nextBytes(80));
    }

    private byte[] hex(String hex) {
        try {
            return Hex.decodeHex(hex.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalStateException(e);
        }
    }
}