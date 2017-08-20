package at.favre.tools.dice.encode;

import at.favre.tools.dice.ui.Arg;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class EncoderHandlerTest {
    @Test
    public void load() throws Exception {
        List<Encoder> encoders = new EncoderHandler().load();

        System.out.println(Arrays.toString(encoders.toArray()));

        for (Encoder encoder : encoders) {
            assertNotNull(encoder.names());
            assertNotNull(encoder.encode(new byte[8]));
            assertTrue(encoder.names().length > 0);
        }

        assertNotNull(encoders);
        assertTrue(encoders.size() > 10);
    }

    @Test
    public void testFindByName() {
        assertNotNull(new EncoderHandler().findByName(Arg.DEFAULT_ENCODING));
    }

    @Test
    public void testDescription() {
        String description = new EncoderHandler().returnRegistryInfo();
        assertNotNull(description);
        assertTrue(description.length() > 100);
        System.out.println(description);
    }
}