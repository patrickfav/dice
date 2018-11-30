package at.favre.tools.dice.service.anuquantum;

import at.favre.lib.bytes.Bytes;
import at.favre.tools.dice.service.ServiceHandler;
import org.junit.Test;

import java.net.UnknownHostException;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

public class AnuQuantumServiceHandlerTest {
    @Test
    public void getRandom() throws Exception {
        ServiceHandler.Result<AnuQuantumResponse> response = new AnuQuantumServiceHandler(true).getRandom();

        assertNotNull(response);

        if (response.throwable != null) {
            response.throwable.printStackTrace();
        }

        if (response.throwable != null && response.throwable instanceof UnknownHostException) {
            System.out.printf(response.errorMsg);
        } else {
            assertNotNull(response.seed);
            assertNotNull(response.response);
            System.out.println(Bytes.from(response.seed).encodeHex());
            assertTrue(response.seed.length == AnuQuantumServiceHandler.ENTROPY_SEED_LENGTH_BYTE);
            assertTrue(response.response.success);
            assertNotEquals(0, response.response.hashCode());
            assertNotNull(response.response.toString());
            assertFalse(response.response.equals(new AnuQuantumResponse("", -1, null, null, false)));
            assertTrue(response.response.length == 1);
            assertTrue(response.response.size == AnuQuantumServiceHandler.ENTROPY_SEED_LENGTH_BYTE);
        }
    }
}
