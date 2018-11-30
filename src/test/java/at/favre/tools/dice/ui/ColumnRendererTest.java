package at.favre.tools.dice.ui;

import at.favre.lib.bytes.Bytes;
import at.favre.tools.dice.DrbgMock;
import at.favre.tools.dice.encode.DefaultEncoderFormat;
import at.favre.tools.dice.encode.Encoder;
import at.favre.tools.dice.encode.EncoderFormat;
import at.favre.tools.dice.encode.EncoderHandler;
import at.favre.tools.dice.encode.byteencoder.Base36Encoder;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ColumnRendererTest {
    private final EncoderFormat encoderFormat = new DefaultEncoderFormat();
    private final ColumnRenderer.RandomGenerator generator = new ColumnRenderer.DefaultRandomGenerator(new Base36Encoder()
            , new DrbgMock(), 6);

    @Test
    public void render() throws Exception {
        new ColumnRenderer(encoderFormat, generator).render(5, System.out, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ColumnRenderer(encoderFormat, generator).render(2, new PrintStream(baos), false);

        String out = baos.toString("UTF-8");
        assertNotNull(out);
        assertTrue(out.length() > 10);
    }

    @Test
    public void renderAutoFill() throws Exception {
        final int maxWordLength = 16;
        final int count = 32;

        for (int i = 1; i < maxWordLength; i += 2) {
            testRender(generator, encoderFormat, count, true, false);
        }
    }

    @Test
    public void renderSingleColumn() throws Exception {
        final int maxWordLength = 16;
        final int count = 32;

        for (int i = 1; i < maxWordLength; i += 2) {
            testRender(generator, encoderFormat, count, true, false);
        }
    }

    @Test
    public void renderMany() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ColumnRenderer(encoderFormat, generator).renderSingleColumn(13, new PrintStream(baos));
        String out = baos.toString("UTF-8");
        assertNotNull(out);
        assertTrue(out.length() > 10);
        System.out.println(out);
        System.out.println();
    }

    private void testRender(ColumnRenderer.RandomGenerator randomGenerator, EncoderFormat encoderFormat, int count, boolean auto, boolean isFile) throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (auto) {
            new ColumnRenderer(encoderFormat, randomGenerator).renderAutoColumn(count, new PrintStream(baos), isFile);
        } else {
            new ColumnRenderer(encoderFormat, randomGenerator).render(count, new PrintStream(baos), isFile);
        }

        String out = baos.toString("UTF-8");
        assertNotNull(out);
        assertTrue(out.length() > 10);
        System.out.println(out);
        System.out.println();
    }

    private List<String> generateRnd(int wordLength, int count) {
        List<String> list = new ArrayList<>(count);
        Random rnd = new Random();
        byte[] data = new byte[wordLength];
        for (int i = 0; i < count; i++) {
            rnd.nextBytes(data);
            list.add(new Base36Encoder().encode(data));
        }
        return list;
    }

    @Test
    public void testAllEncoders() throws Exception {
        int count = 9;
        for (Encoder encoder : new EncoderHandler().load()) {
            for (int i = 1; i < 9; i += 7) {
                testRender(new ColumnRenderer.DefaultRandomGenerator(encoder
                        , new DrbgMock(), 2 * i), encoder.getEncoderFormat(), count, false, true);
                testRender(new ColumnRenderer.DefaultRandomGenerator(encoder
                        , new DrbgMock(), 2 * i), encoder.getEncoderFormat(), count, false, false);
            }

        }
    }

    private List<String> generateRnd(Encoder encoder, int length, int count) {
        List<String> out = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            out.add(encoder.encode(Bytes.random(length).array()));
        }
        return out;
    }
}
