package at.favre.tools.dice.service;

import at.favre.tools.dice.util.ByteUtils;
import org.junit.Test;

import static at.favre.tools.dice.service.RandomOrgServiceHandler.ENTROPY_SEED_LENGTH_BIT;
import static org.junit.Assert.*;

/**
 * Created by PatrickF on 17.08.2017.
 */
public class RandomOrgServiceHandlerTest {
    @Test
    public void getRandom() throws Exception {
        RandomOrgServiceHandler.Result random = new RandomOrgServiceHandler(true).getRandom();
        System.out.println(ByteUtils.bytesToHex(random.seed));
        assertNotNull(random.seed);
        assertNotNull(random.response);
        System.out.println(random.response.toString());

        assertFalse(random.response.equals(1));
        assertFalse(random.response.hashCode() == 0);
        assertTrue(random.seed.length == ENTROPY_SEED_LENGTH_BIT / 8);
    }

}