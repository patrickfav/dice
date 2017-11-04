package at.favre.tools.dice.encode;

import at.favre.lib.bytes.Bytes;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class StringEncodingTest {
    @Test
    public void testStringEncoding() {
        for (int x = 0; x < 5; x++) {
            for (int i = 0; i < 32; i++) {
                byte[] testArray = Bytes.random(i).array();
                String hex = Bytes.from(testArray).encodeHex(true);
                String encoded = new String(testArray, StandardCharsets.ISO_8859_1);
                System.out.println(encoded);
                String hexStringEncoded = Bytes.from(encoded, StandardCharsets.ISO_8859_1).encodeHex(true);

                System.out.println(hex);
                System.out.println(hexStringEncoded);
                assertEquals(hex, hexStringEncoded);
            }
        }
    }

}
