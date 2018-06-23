package at.favre.tools.dice.util;

import at.favre.lib.bytes.Bytes;
import at.favre.lib.bytes.BytesTransformers;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ByteUtilsTest {
    @Test
    public void appendCrc32Checksum() {
        byte[] b1 = Bytes.random(32).array();

        byte[] b1PlusCrc32 = BytesTransformers.checksumAppendCrc32().transform(b1, false);
        assertEquals(b1.length + 4, b1PlusCrc32.length);
        assertFalse(Arrays.equals(b1, b1PlusCrc32));
    }
}