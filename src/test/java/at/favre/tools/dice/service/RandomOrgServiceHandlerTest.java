package at.favre.tools.dice.service;

import at.favre.tools.dice.util.ByteUtils;
import org.junit.Test;

import java.net.UnknownHostException;

import static at.favre.tools.dice.service.RandomOrgServiceHandler.ENTROPY_SEED_LENGTH_BIT;
import static org.junit.Assert.*;

/**
 * Created by PatrickF on 17.08.2017.
 */
public class RandomOrgServiceHandlerTest {
    @Test
    public void getRandom() throws Exception {
        RandomOrgServiceHandler.Result response = new RandomOrgServiceHandler(true).getRandom();

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
            System.out.println(response.response.toString());

            assertFalse(response.response.equals(1));
            assertFalse(response.response.hashCode() == 0);
            assertTrue(response.seed.length == ENTROPY_SEED_LENGTH_BIT / 8);
        }
    }

}