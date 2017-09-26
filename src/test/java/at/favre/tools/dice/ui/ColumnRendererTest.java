package at.favre.tools.dice.ui;

import at.favre.tools.dice.encode.DefaultEncoderFormat;
import at.favre.tools.dice.encode.EncoderFormat;
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

        int targetWidth = 40;
        new ColumnRenderer(encoderFormat, targetWidth).render(elements, System.out, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ColumnRenderer(encoderFormat, targetWidth).render(elements, new PrintStream(baos), false);

        String out = baos.toString("UTF-8");
        assertNotNull(out);
        assertTrue(out.length() > 10);
    }

    @Test
    public void renderAutoFill() throws Exception {
        final int maxWordLength = 16;
        final int count = 32;
        final int targetWidth = 80;

        for (int i = 1; i < maxWordLength; i += 2) {
            List<String> elements = generateRnd(i, count + 20);
            testRender(count, targetWidth, elements, true);
        }
    }

    @Test
    public void renderSingleColumn() throws Exception {
        final int maxWordLength = 16;
        final int count = 32;
        final int targetWidth = 80;

        for (int i = 1; i < maxWordLength; i += 2) {
            List<String> elements = generateRnd(i, count + 20);
            testRender(count, targetWidth, elements, true);
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

    private void testRender(int count, int targetWidth, List<String> elements, boolean auto) throws UnsupportedEncodingException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if (auto) {
            new ColumnRenderer(encoderFormat, targetWidth).renderAutoColumn(count, elements, new PrintStream(baos), false);
        } else {
            new ColumnRenderer(encoderFormat, targetWidth).render(elements, new PrintStream(baos), false);
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

}