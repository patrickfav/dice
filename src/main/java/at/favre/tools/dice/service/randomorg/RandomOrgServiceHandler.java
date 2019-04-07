/*
 *  Copyright 2017 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package at.favre.tools.dice.service.randomorg;

import at.favre.tools.dice.service.AServiceHandler;
import at.favre.tools.dice.service.randomorg.model.RandomOrgBlobRequest;
import at.favre.tools.dice.service.randomorg.model.RandomOrgBlobResponse;
import com.github.fzakaria.ascii85.Ascii85;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.apache.commons.codec.binary.Base64;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;

/**
 * RANDOM.ORG offers true random numbers to anyone on the Internet.
 * The randomness comes from atmospheric noise.
 * <p>
 * See https://www.random.org/
 */
public final class RandomOrgServiceHandler extends AServiceHandler {
    static final int ENTROPY_SEED_LENGTH_BIT = 192;

    public RandomOrgServiceHandler(boolean debug) {
        super(debug);
    }

    @Override
    public Result<RandomOrgBlobResponse> getRandom() {
        Instant startTime = Instant.now();

        OkHttpClient client = createClient();
        Exception error = null;
        String errMsg = null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.random.org/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        RandomOrgService service = retrofit.create(RandomOrgService.class);

        try {
            String k = new String(Ascii85.decode(API_KEY), StandardCharsets.UTF_8);
            final RandomOrgBlobRequest randomOrgBlobRequest = new RandomOrgBlobRequest(new RandomOrgBlobRequest.Params(k, 1, ENTROPY_SEED_LENGTH_BIT));

            Response<String> response = service.getRandom(createHeaderMap(), randomOrgBlobRequest).execute();
            if (response != null && response.isSuccessful() && response.body() != null) {
                String rawResponse = response.body();
                RandomOrgBlobResponse orgBlobResponse = new Gson().fromJson(rawResponse, RandomOrgBlobResponse.class);

                if (orgBlobResponse.id != randomOrgBlobRequest.id) {
                    throw new IllegalArgumentException("json rpc id do not match");
                }

                if (!RandomOrgUtil.base64EncodedSha512(k).equals(orgBlobResponse.result.random.hashedApiKey)) {
                    throw new IllegalArgumentException("used api key does not match");
                }

                if (orgBlobResponse.result.bitsLeft <= ENTROPY_SEED_LENGTH_BIT / 8) {
                    throw new IllegalArgumentException("api token ran out of entropy quota");
                }

                if (orgBlobResponse.result.requestsLeft <= 1 / 8) {
                    throw new IllegalArgumentException("api token ran out of request quota");
                }

                if (!RandomOrgUtil.verifySignature(rawResponse, orgBlobResponse.result.signature)) {
                    throw new IllegalArgumentException("response signature could not be verified");
                }

                return new Result<>(getName(), new Base64().decode(orgBlobResponse.result.random.data[0]), orgBlobResponse, Duration.between(startTime, Instant.now()).toNanos());
            }

        } catch (UnknownHostException e) {
            error = e;
            errMsg = "Cannot resolve host. Is the device offline?";
        } catch (Exception e) {
            error = e;
            errMsg = "Error during http request: " + e.getMessage();
        }

        return new Result<>(getName(), error, errMsg);
    }

    @Override
    public String getName() {
        return "random.org";
    }

    private static final String API_KEY = (new Object() {
        int t;

        public String toString() {
            byte[] buf = new byte[46];
            t = -1343470464;
            buf[0] = (byte) (t >>> 1);
            t = 859390036;
            buf[1] = (byte) (t >>> 10);
            t = 650102407;
            buf[2] = (byte) (t >>> 17);
            t = 594361199;
            buf[3] = (byte) (t >>> 23);
            t = 1003872916;
            buf[4] = (byte) (t >>> 1);
            t = 50203844;
            buf[5] = (byte) (t >>> 6);
            t = -1782898035;
            buf[6] = (byte) (t >>> 23);
            t = -1638246212;
            buf[7] = (byte) (t >>> 16);
            t = -1786625795;
            buf[8] = (byte) (t >>> 22);
            t = -514323577;
            buf[9] = (byte) (t >>> 16);
            t = 1662917823;
            buf[10] = (byte) (t >>> 2);
            t = -63125052;
            buf[11] = (byte) (t >>> 5);
            t = 2076225704;
            buf[12] = (byte) (t >>> 10);
            t = -642251809;
            buf[13] = (byte) (t >>> 4);
            t = -417482464;
            buf[14] = (byte) (t >>> 15);
            t = -1123269373;
            buf[15] = (byte) (t >>> 2);
            t = -1245186773;
            buf[16] = (byte) (t >>> 23);
            t = 1191842577;
            buf[17] = (byte) (t >>> 11);
            t = 324273342;
            buf[18] = (byte) (t >>> 22);
            t = 1234029784;
            buf[19] = (byte) (t >>> 10);
            t = 1070123234;
            buf[20] = (byte) (t >>> 10);
            t = 1284930982;
            buf[21] = (byte) (t >>> 21);
            t = 1207286490;
            buf[22] = (byte) (t >>> 10);
            t = -232224298;
            buf[23] = (byte) (t >>> 3);
            t = -1909907061;
            buf[24] = (byte) (t >>> 10);
            t = -1854323093;
            buf[25] = (byte) (t >>> 5);
            t = -1878508264;
            buf[26] = (byte) (t >>> 13);
            t = -1138270700;
            buf[27] = (byte) (t >>> 15);
            t = 1425984716;
            buf[28] = (byte) (t >>> 24);
            t = -502416095;
            buf[29] = (byte) (t >>> 2);
            t = 545446717;
            buf[30] = (byte) (t >>> 23);
            t = 1741403157;
            buf[31] = (byte) (t >>> 7);
            t = -1654565139;
            buf[32] = (byte) (t >>> 6);
            t = 583867571;
            buf[33] = (byte) (t >>> 1);
            t = -116209101;
            buf[34] = (byte) (t >>> 6);
            t = 1079655329;
            buf[35] = (byte) (t >>> 24);
            t = 1677284378;
            buf[36] = (byte) (t >>> 10);
            t = 2048538777;
            buf[37] = (byte) (t >>> 14);
            t = 657528840;
            buf[38] = (byte) (t >>> 21);
            t = -1276438974;
            buf[39] = (byte) (t >>> 7);
            t = 2113720162;
            buf[40] = (byte) (t >>> 10);
            t = -194438862;
            buf[41] = (byte) (t >>> 10);
            t = -228353361;
            buf[42] = (byte) (t >>> 6);
            t = 1521767368;
            buf[43] = (byte) (t >>> 22);
            t = 1712621755;
            buf[44] = (byte) (t >>> 14);
            t = -1513066824;
            buf[45] = (byte) (t >>> 11);
            return new String(buf);
        }
    }.toString());
}
