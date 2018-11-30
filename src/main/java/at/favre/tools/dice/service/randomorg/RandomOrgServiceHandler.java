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
        long startTime = System.currentTimeMillis();

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

                return new Result<>(getName(), new Base64().decode(orgBlobResponse.result.random.data[0]), orgBlobResponse, System.currentTimeMillis() - startTime);
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
            byte[] buf = new byte[45];
            t = 1000367711;
            buf[0] = (byte) (t >>> 15);
            t = -1871864484;
            buf[1] = (byte) (t >>> 7);
            t = 1594388290;
            buf[2] = (byte) (t >>> 24);
            t = -568584270;
            buf[3] = (byte) (t >>> 7);
            t = -563899139;
            buf[4] = (byte) (t >>> 4);
            t = 307285659;
            buf[5] = (byte) (t >>> 10);
            t = -896104967;
            buf[6] = (byte) (t >>> 9);
            t = 1217941539;
            buf[7] = (byte) (t >>> 18);
            t = 1867967392;
            buf[8] = (byte) (t >>> 5);
            t = 790145698;
            buf[9] = (byte) (t >>> 1);
            t = 113973157;
            buf[10] = (byte) (t >>> 7);
            t = -1702145170;
            buf[11] = (byte) (t >>> 8);
            t = 649802833;
            buf[12] = (byte) (t >>> 24);
            t = 2064760786;
            buf[13] = (byte) (t >>> 14);
            t = -1836792610;
            buf[14] = (byte) (t >>> 5);
            t = -1496532134;
            buf[15] = (byte) (t >>> 14);
            t = 1653439095;
            buf[16] = (byte) (t >>> 20);
            t = -1522116684;
            buf[17] = (byte) (t >>> 6);
            t = -458154848;
            buf[18] = (byte) (t >>> 17);
            t = -1569403986;
            buf[19] = (byte) (t >>> 23);
            t = -1020631789;
            buf[20] = (byte) (t >>> 20);
            t = 514873341;
            buf[21] = (byte) (t >>> 6);
            t = 191102092;
            buf[22] = (byte) (t >>> 16);
            t = 459844665;
            buf[23] = (byte) (t >>> 22);
            t = -1346014790;
            buf[24] = (byte) (t >>> 2);
            t = -449570581;
            buf[25] = (byte) (t >>> 7);
            t = -1132416235;
            buf[26] = (byte) (t >>> 4);
            t = 31633483;
            buf[27] = (byte) (t >>> 7);
            t = 1187561302;
            buf[28] = (byte) (t >>> 13);
            t = 1268647811;
            buf[29] = (byte) (t >>> 19);
            t = 538005300;
            buf[30] = (byte) (t >>> 23);
            t = -963384439;
            buf[31] = (byte) (t >>> 3);
            t = 782616237;
            buf[32] = (byte) (t >>> 13);
            t = 43791692;
            buf[33] = (byte) (t >>> 8);
            t = 682744933;
            buf[34] = (byte) (t >>> 23);
            t = -1799180324;
            buf[35] = (byte) (t >>> 18);
            t = 307442394;
            buf[36] = (byte) (t >>> 19);
            t = -515198448;
            buf[37] = (byte) (t >>> 18);
            t = 332599428;
            buf[38] = (byte) (t >>> 2);
            t = 1171297554;
            buf[39] = (byte) (t >>> 24);
            t = 899943885;
            buf[40] = (byte) (t >>> 12);
            t = 2011125994;
            buf[41] = (byte) (t >>> 2);
            t = 1420602448;
            buf[42] = (byte) (t >>> 9);
            t = 1281158972;
            buf[43] = (byte) (t >>> 10);
            t = -1581405729;
            buf[44] = (byte) (t >>> 5);
            return new String(buf);
        }
    }.toString());
}
