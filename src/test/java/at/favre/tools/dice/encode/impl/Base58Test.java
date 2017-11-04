package at.favre.tools.dice.encode.impl;

import at.favre.lib.bytes.Bytes;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class Base58Test {

    @Test
    public void testEncodeBitcoin() throws Exception {
        testEncode(Base58.BitcoinEncoder());
    }

    @Test
    public void testEncodeRipple() throws Exception {
        testEncode(Base58.RippleEncoder());
    }

    @Test
    public void testEncodeFlickr() throws Exception {
        testEncode(Base58.FlickrEncoder());
    }

    private void testEncode(Base58 base58) throws Exception {
        for (int i = 8; i < 64; i += 8) {
            byte[] randomBytes = Bytes.random(i).array();
            String encoded = base58.encode(randomBytes);
            System.out.println(encoded);
            assertNotNull(encoded);
            assertTrue(encoded.length() >= i);

            byte[] decoded = base58.decode(encoded);
            assertTrue(Arrays.equals(randomBytes, decoded));
        }
    }

}