package at.favre.tools.dice.encode;

import at.favre.lib.bytes.Bytes;
import at.favre.tools.dice.encode.byteencoder.*;
import at.favre.tools.dice.encode.textencoder.Utf8Encoder;
import org.jetbrains.annotations.NotNull;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.GZIPOutputStream;

@Ignore("simple test for the best encoding while compressed - only run on demand")
public class CompressionTest {

    @Test
    public void testBase32() {
        for (int i = 0; i < 127; i++) {
            System.out.println(new Base32Encoder().encodePadded(new byte[]{(byte) i, (byte) i}));
        }
    }

    @Test
    public void testBase64() {
        for (int i = -128; i < 127; i++) {
            System.out.println(new Base64Encoder(false).encodePadded(new byte[]{(byte) i}));
        }
    }

    @Test
    public void testBaseLengths() {
        for (int i = 4; i < 17; i += 4) {
            Bytes subject = Bytes.allocate(i,(byte) (0xFE));
            System.out.println("\nLength: "+i+" bytes\n");
            System.out.println(subject.encodeBinary());
            System.out.println(subject.encodeOctal());
            System.out.println(subject.encodeDec());
            System.out.println(subject.encodeHex());
            System.out.println(new Base26Encoder().encodePadded(subject.array()));
            System.out.println(subject.encodeBase32());
            System.out.println(subject.encodeRadix(36));
            System.out.println(new Base58Encoder.BitcoinStyle().encodePadded(subject.array()));
            System.out.println(subject.encodeBase64());
            System.out.println(new Base85Encoder().encodePadded(subject.array()));
        }

        for (int i = 4; i < 17; i += 4) {
            Bytes subject = Bytes.allocate(i,(byte) (0x00));
            System.out.println("\nLength: "+i+" bytes\n");
            System.out.println(subject.encodeBinary());
            System.out.println(subject.encodeOctal());
            System.out.println(subject.encodeDec());
            System.out.println(subject.encodeHex());
            System.out.println(new Base26Encoder().encodePadded(subject.array()));
            System.out.println(subject.encodeBase32());
            System.out.println(subject.encodeRadix(36));
            System.out.println(new Base58Encoder.BitcoinStyle().encodePadded(subject.array()));
            System.out.println(subject.encodeBase64());
            System.out.println(new Base85Encoder().encodePadded(subject.array()));
        }
    }

    @Test
    public void testRandomData() throws Exception {
        testAllByteEncoder(gen(1024));
        testAllByteEncoder(gen(1024 * 128));
    }

    @Test
    public void testImage1() throws Exception {
        File imageFile = new File(getClass().getClassLoader().getResource("example_image1.base64").toURI().getPath());
        String content = new String(Files.readAllBytes(imageFile.toPath()), StandardCharsets.UTF_8);
        testAllByteEncoder(content.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    public void testLogData() throws Exception {
        File logFile1 = new File(getClass().getClassLoader().getResource("example_log1.txt").toURI().getPath());
        String logContent1 = new String(Files.readAllBytes(logFile1.toPath()), StandardCharsets.UTF_8);
        testAllByteEncoder(logContent1.getBytes(StandardCharsets.UTF_8));
    }

    private void testAllByteEncoder(byte[] data) throws Exception {
        byte[] compressedOriginal = compress(data);
        System.out.println("\n**********************************************\n");
        System.out.println("Original size: \t\t\t" + data.length + " byte");
        System.out.println("Compressed size (raw): \t" + compressedOriginal.length + " byte\n");

        EncoderHandler handler = new EncoderHandler();

        List<Result> resultList = new ArrayList<>();
        handler.load().forEach(encoder -> {
            if (encoder instanceof AByteEncoder &&
                    !(encoder instanceof Utf8Encoder) &&
                    !(encoder instanceof Base64Encoder.UrlSafe) &&
                    !(encoder instanceof Base58Encoder.BitcoinStyle) &&
                    !(encoder instanceof Base16Encoder.Base16UpperCaseEncoder)) {
                resultList.add(testCompression(data, encoder));
            }
        });

        Collections.sort(resultList);

        printTable(resultList);

        System.out.println("\nBest 3 results for encoded:");
        printResult(resultList.get(0));
        printResult(resultList.get(1));
        printResult(resultList.get(2));

        resultList.sort(Comparator.comparingInt(o -> o.encodedCompressedSizeBytes));
        System.out.println("\nBest 3 results for encoded & compressed:");
        printResult(resultList.get(0));
        printResult(resultList.get(1));
        printResult(resultList.get(2));
    }

    private Result testCompression(byte[] raw, Encoder encoder) {
        try {
            String encoded = encoder.encode(raw);
            byte[] compressedEncoded = compress(encoded.getBytes(StandardCharsets.UTF_8));

            Result result = new Result(
                    encoder,
                    encoder.names()[0],
                    raw.length,
                    encoded.getBytes(StandardCharsets.UTF_8).length,
                    compress(raw).length,
                    compressedEncoded.length
            );

            return result;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static void printResult(Result result) {
        System.out.println("\n[" + result.name + "]");
        System.out.println("Encoded size: \t\t\t" + result.encodedSizeBytes + " byte (" + differencePercent(result.originalSizeBytes, result.encodedSizeBytes) + ")");
        System.out.println("Compressed size (enc): \t" + result.encodedCompressedSizeBytes + " byte (" + differencePercent(result.compressedSizeBytes, result.encodedCompressedSizeBytes) + ")");
    }

    private static void printTable(List<Result> results) {
        final int defPadding = 10;
        System.out.println(getPaddedString("Name", defPadding) + "\t" +
                getPaddedString("enc", defPadding) + "\t" +
                getPaddedString("diff", defPadding / 2) + "\t" +
                getPaddedString("%", 4) + "\t" +
                getPaddedString("enc-comp", defPadding) + "\t" +
                getPaddedString("diff", defPadding / 2));
        System.out.println("------------------------------------------------------------------");

        results.forEach(result ->
                System.out.println(getPaddedString(result.name, defPadding) + "\t" +
                        getPaddedNumber(result.encodedSizeBytes, defPadding) + "\t" +
                        getPaddedNumber(result.differenceEncoded, defPadding / 2) + "\t" +
                        getPaddedNumber(result.differenceEncoded * 100 / result.encodedSizeBytes, 4) + "%\t" +
                        getPaddedNumber(result.encodedCompressedSizeBytes, defPadding) + "\t" +
                        getPaddedNumber(result.differeneceEncodedCompressed, defPadding / 2) + "\t" +
                        getPaddedNumber(result.differeneceEncodedCompressed * 100 / result.encodedCompressedSizeBytes, 4) + "%"
                ));
    }

    private static String getPaddedNumber(int number, int minPaddingLength) {
        return String.format("%" + minPaddingLength + "d", number);
    }

    private static String getPaddedString(String s, int minPaddingLength) {
        return String.format("%" + minPaddingLength + "s", s);
    }

    private static String differencePercent(int originalLength, int differentLength) {
        int byteDiff = differentLength - originalLength;
        int percent = (byteDiff * 100) / differentLength;
        return byteDiff + " byte [+" + percent + "%]";
    }

    private static byte[] compress(byte[] data) throws Exception {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length)) {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(bos);
            gzipOutputStream.write(data);
            gzipOutputStream.close();
            return bos.toByteArray();
        }
    }

    private static byte[] gen(int length) {
        byte[] temp = new byte[length];
        new Random().nextBytes(temp);
        return temp;
    }

    private static class Result implements Comparable<Result> {
        public final Encoder encoder;
        public final String name;
        public final int originalSizeBytes;
        public final int encodedSizeBytes;
        public final int compressedSizeBytes;
        public final int encodedCompressedSizeBytes;
        public final int differenceEncoded;
        public final int differeneceEncodedCompressed;

        public Result(Encoder encoder, String name, int originalSizeBytes, int encodedSizeBytes, int compressedSizeBytes, int encodedCompressedSizeBytes) {
            this.encoder = encoder;
            this.name = name;
            this.originalSizeBytes = originalSizeBytes;
            this.encodedSizeBytes = encodedSizeBytes;
            this.compressedSizeBytes = compressedSizeBytes;
            this.encodedCompressedSizeBytes = encodedCompressedSizeBytes;
            this.differenceEncoded = encodedSizeBytes - originalSizeBytes;
            this.differeneceEncodedCompressed = encodedCompressedSizeBytes - compressedSizeBytes;
        }

        @Override
        public int compareTo(@NotNull CompressionTest.Result o) {
            return Integer.valueOf(encodedSizeBytes).compareTo(o.encodedSizeBytes);
        }
    }
}
