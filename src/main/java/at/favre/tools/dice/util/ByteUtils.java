package at.favre.tools.dice.util;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.zip.CRC32;

/**
 * Created by PatrickF on 01.06.2017.
 */
public class ByteUtils {
    private final static char[] HEX_REF_TABLE = "0123456789ABCDEF".toCharArray();

    public static String byteToHex(byte aByte) {
        return bytesToHex(new byte[]{aByte});
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_REF_TABLE[v >>> 4];
            hexChars[j * 2 + 1] = HEX_REF_TABLE[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexToBytes(String hexString) {
        if (hexString.length() % 2 == 1) {
            hexString = "0" + hexString;
        }

        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] concatAll(byte[] first, byte[]... rest) {
        int totalLength = first.length;
        for (byte[] array : rest) {
            totalLength += array.length;
        }

        byte[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;

        for (byte[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    public static byte[] xor(byte[] first, byte[] second) {
        if (first.length != second.length) {
            throw new IllegalArgumentException("all byte array must be of same length");
        }
        byte[] xored = new byte[first.length];

        for (int i = 0; i < first.length; i++) {
            xored[i] = (byte) (first[i] ^ second[i]);
        }
        return xored;
    }

    public static byte[] intToByte(int integer) {
        return ByteBuffer.allocate(4).putInt(integer).array();
    }

    public static byte[] copy(byte[] orig) {
        byte[] copy = new byte[orig.length];
        System.arraycopy(orig, 0, copy, 0, orig.length);
        return copy;
    }

    public static byte[] unsecureRandomBytes(int length) {
        byte[] out = new byte[length];
        new Random().nextBytes(out);
        return out;
    }

    public static List<Byte> toList(byte[] arr) {
        List<Byte> list = new ArrayList<>();
        for (byte b : arr) {
            list.add(b);
        }
        return list;
    }

    /**
     * Will append 4 bytes of CRC32 checksum after the original byte array,
     * making it 4 bytes longer
     *
     * @param original
     * @return original || crc32
     */
    public static byte[] appendCrc32(byte[] original) {
        CRC32 crc32 = new CRC32();
        crc32.update(original);
        byte[] checksum = ByteBuffer.allocate(Long.BYTES).putLong(crc32.getValue()).array();
        byte[] checksum32 = new byte[4];
        System.arraycopy(checksum, 4, checksum32, 0, checksum32.length);
        return concatAll(original, checksum32);
    }
}
