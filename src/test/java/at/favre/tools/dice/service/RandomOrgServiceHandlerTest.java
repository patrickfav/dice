package at.favre.tools.dice.service;

import at.favre.tools.dice.util.ByteUtils;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by PatrickF on 17.08.2017.
 */
public class RandomOrgServiceHandlerTest {
    @Test
    public void getRandom() throws Exception {
        RandomOrgServiceHandler.Result random = new RandomOrgServiceHandler(false).getRandom();
        System.out.println(ByteUtils.bytesToHex(random.seed));
        assertTrue(random.seed.length == 16);
    }

}