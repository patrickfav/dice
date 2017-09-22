package at.favre.tools.dice.service.anuquantum;

import at.favre.tools.dice.service.AServiceHandler;
import at.favre.tools.dice.util.ByteUtils;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AnuQuantumServiceHandlerTest {
    @Test
    public void getRandom() throws Exception {
        AServiceHandler.Result<AnuQuantomResponse> response = new AnuQuantumServiceHandler(true).getRandom();

        assertNotNull(response);

        if (response.throwable != null) {
            response.throwable.printStackTrace();
        }

        if (response.throwable != null && response.throwable instanceof UnknownHostException) {
            System.out.printf(response.errorMsg);
        } else {
            assertNotNull(response.seed);
            assertNotNull(response.response);
            System.out.println(ByteUtils.bytesToHex(response.seed));
            assertTrue(response.seed.length == AnuQuantumServiceHandler.ENTROPY_SEED_LENGTH_BYTE);
            assertTrue(response.response.success);
            assertTrue(response.response.length == 1);
            assertTrue(response.response.size == AnuQuantumServiceHandler.ENTROPY_SEED_LENGTH_BYTE);
        }
    }
}