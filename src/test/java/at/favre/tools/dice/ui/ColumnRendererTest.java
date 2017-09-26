package at.favre.tools.dice.ui;

import at.favre.tools.dice.encode.DefaultEncoderFormat;
import at.favre.tools.dice.encode.Encoder;
import at.favre.tools.dice.encode.EncoderFormat;
import at.favre.tools.dice.encode.EncoderHandler;
import at.favre.tools.dice.encode.byteencoder.Base36Encoder;
import at.favre.tools.dice.util.ByteUtils;
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

    @Test
    public void render() throws Exception {
        List<String> elements = new ArrayList<>();
        elements.add("hallo");
        elements.add("hallooooo");
        elements.add("mu");
        elements.add("mukka");
        elements.add("asdaslhhhhaad");
        elements.add("kammdhha");
        elements.add("haud");
        elements.add("adw2");
        elements.add("dasd");
        elements.add("cac");

        new ColumnRenderer(encoderFormat).render(elements, System.out, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ColumnRenderer(encoderFormat).render(elements, new PrintStream(baos), false);

        String out = baos.toString("UTF-8");
        assertNotNull(out);
        assertTrue(out.length() > 10);
    }

    @Test
    public void renderAutoFill() throws Exception {
        final int maxWordLength = 16;
        final int count = 32;

        for (int i = 1; i < maxWordLength; i += 2) {
            List<String> elements = generateRnd(i, count + 20);
            testRender(encoderFormat, count, elements, true, false);
        }
    }

    @Test
    public void renderSingleColumn() throws Exception {
        final int maxWordLength = 16;
        final int count = 32;

        for (int i = 1; i < maxWordLength; i += 2) {
            List<String> elements = generateRnd(i, count + 20);
            testRender(encoderFormat, count, elements, true, false);
        }
    }

    @Test
    public void renderMany() throws Exception {
        final int count = 13;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        List<String> elements = generateRnd(4, count);
        new ColumnRenderer(encoderFormat).renderSingleColumn(elements, new PrintStream(baos));
        String out = baos.toString("UTF-8");
        assertNotNull(out);
        assertTrue(out.length() > 10);
        System.out.println(out);
        System.out.println();
    }

    private void testRender(EncoderFormat encoderFormat, int count, List<String> elements, boolean auto, boolean isFile) throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (auto) {
            new ColumnRenderer(encoderFormat).renderAutoColumn(count, elements, new PrintStream(baos), isFile);
        } else {
            new ColumnRenderer(encoderFormat).render(elements, new PrintStream(baos), isFile);
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
            List<String> rnds = generateRnd(encoder, 6, count);
            testRender(encoder.getEncoderFormat(), count, rnds, false, true);
            testRender(encoder.getEncoderFormat(), count, rnds, false, false);
        }
    }

    private List<String> generateRnd(Encoder encoder, int length, int count) {
        List<String> out = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            out.add(encoder.encode(ByteUtils.unsecureRandomBytes(length)));
        }
        return out;
    }
}