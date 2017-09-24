package at.favre.tools.dice.encode;

import at.favre.tools.dice.util.ByteUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class StringEncodingTest {
    @Test
    public void testStringEncoding() {
        for (int x = 0; x < 5; x++) {
            for (int i = 0; i < 32; i++) {
                byte[] testArray = ByteUtils.unsecureRandomBytes(i);
                String hex = ByteUtils.bytesToHex(testArray);

                String encoded = new String(testArray, StandardCharsets.ISO_8859_1);
                System.out.println(encoded);
                String hexStringEncoded = ByteUtils.bytesToHex(encoded.getBytes(StandardCharsets.ISO_8859_1));

                System.out.println(hex);
                System.out.println(hexStringEncoded);
                assertEquals(hex, hexStringEncoded);
            }
        }
    }

}
