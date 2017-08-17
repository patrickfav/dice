package at.favre.tools.dice.service;

import at.favre.tools.dice.util.ByteUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by PatrickF on 17.08.2017.
 */
public class RandomOrgServiceHandlerTest {
    @Test
    public void getRandom() throws Exception {
        byte[] random = new RandomOrgServiceHandler(false).getRandom();
        System.out.println(ByteUtils.bytesToHex(random));
        assertTrue(random.length == 16);
    }

}