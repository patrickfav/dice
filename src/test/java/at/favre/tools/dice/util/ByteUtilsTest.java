package at.favre.tools.dice.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ByteUtilsTest {
    @Test
    public void byteToHex() throws Exception {
        assertEquals("00", ByteUtils.byteToHex((byte) 0));
    }

    @Test
    public void bytesToHex() throws Exception {
        assertEquals("0000", ByteUtils.bytesToHex(new byte[2]));
    }

    @Test
    public void hexToBytes() throws Exception {
        assertArrayEquals(new byte[2], ByteUtils.hexToBytes("0000"));
    }

    @Test
    public void concatAll() throws Exception {
        assertArrayEquals(new byte[]{0, 1}, ByteUtils.concatAll(new byte[]{0}, new byte[]{1}));
    }

    @Test
    public void xor() throws Exception {
        assertArrayEquals(new byte[]{0, 0}, ByteUtils.xor(new byte[]{0, 1}, new byte[]{0, 1}));
    }

    @Test
    public void intToByte() throws Exception {
        assertArrayEquals(new byte[]{0, 0, 0, 0}, ByteUtils.intToByte(0));

    }

    @Test
    public void copy() throws Exception {
        byte[] b1 = new byte[]{0, 0, 0, 0};

        assertArrayEquals(b1, ByteUtils.copy(b1));
        assertNotSame(b1, ByteUtils.copy(b1));
    }

    @Test
    public void appendCrc32Checksum() throws Exception {
        byte[] b1 = ByteUtils.unsecureRandomBytes(32);

        byte[] b1PlusCrc32 = ByteUtils.appendCrc32(b1);
        assertTrue(b1.length + 4 == b1PlusCrc32.length);
        assertFalse(Arrays.equals(b1, b1PlusCrc32));
    }
}