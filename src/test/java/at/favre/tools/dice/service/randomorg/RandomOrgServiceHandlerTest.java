package at.favre.tools.dice.service.randomorg;

import at.favre.lib.bytes.Bytes;
import at.favre.tools.dice.service.randomorg.model.RandomOrgBlobResponse;
import org.junit.Test;

import java.net.UnknownHostException;

import static at.favre.tools.dice.service.randomorg.RandomOrgServiceHandler.ENTROPY_SEED_LENGTH_BIT;
import static org.junit.Assert.*;

/**
 * Created by PatrickF on 17.08.2017.
 */
public class RandomOrgServiceHandlerTest {
    @Test
    public void getRandom() throws Exception {
        RandomOrgServiceHandler.Result<RandomOrgBlobResponse> response = new RandomOrgServiceHandler(true).getRandom();

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
            System.out.println(response.response.toString());
            assertNotEquals(-1, response.responseTimeMs());
            assertNotEquals(-1, response.durationNanos);
            assertNotEquals(1, response.response);
            assertNotEquals(0, response.response.hashCode());
            assertEquals(response.seed.length, ENTROPY_SEED_LENGTH_BIT / 8);
        }
    }

    @Test
    public void testResponseModel() {
        RandomOrgBlobResponse response = new RandomOrgBlobResponse("jsonrpc 2", 44,
                new RandomOrgBlobResponse.Result(
                        new RandomOrgBlobResponse.Random(new String[]{"1", "2"}, "api", "base64", "1", 2),
                        "sig", 4, 1, 4, 6));

        assertNotNull(response.toString());
        assertNotEquals(0, response.hashCode());
        assertEquals(response, response);
        assertNotEquals("muh", response);
    }

}
