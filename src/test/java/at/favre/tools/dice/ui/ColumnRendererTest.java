package at.favre.tools.dice.ui;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ColumnRendererTest {
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
        new ColumnRenderer(targetWidth).render(elements, System.out);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new ColumnRenderer(targetWidth).render(elements, new PrintStream(baos));

        String out = baos.toString("UTF-8");
        assertNotNull(out);
        assertTrue(out.length() > 10);
    }

}