package at.favre.tools.dice.encode;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LoaderTest {
    @Test
    public void load() throws Exception {
        List<Encoder> encoders = new Loader().load();

        System.out.println(Arrays.toString(encoders.toArray()));

        for (Encoder encoder : encoders) {
            assertNotNull(encoder.names());
            assertNotNull(encoder.encode(new byte[8]));
            assertTrue(encoder.names().length > 0);
        }

        assertNotNull(encoders);
        assertTrue(encoders.size() > 10);
    }
}