package at.favre.tools.dice.service;

import at.favre.tools.dice.RndTool;
import at.favre.tools.dice.service.model.RandomOrgBlobRequest;
import at.favre.tools.dice.service.model.RandomOrgBlobResponse;
import at.favre.tools.dice.util.RandomOrgUtil;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.codec.binary.Base64;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class RandomOrgServiceHandler {
    final static int ENTROPY_SEED_LENGTH_BIT = 192;
    private final boolean debug;
    private final static String USER_AGENT = "dice/" + RndTool.jarVersion() + " (" + System.getProperty("os.name") + "; Java " + System.getProperty("java.version") + ") github.com/patrickfav/dice";

    public RandomOrgServiceHandler(boolean debug) {
        this.debug = debug;
    }

    /*
     * It will be possible to convert beta keys production keys before the beta ends on 31 December 2017.
     */
    private static final String API_KEY = "ae169728-dec5-4771-b2e8-cc1801aaad70";

    public Result getRandom() {
        long startTime = System.currentTimeMillis();

        OkHttpClient client;
        Exception error = null;
        String errMsg = null;
        if (debug) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder().addNetworkInterceptor(interceptor).addInterceptor(interceptor).build();
        } else {
            client = new OkHttpClient.Builder().build();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.random.org/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        RandomOrgService service = retrofit.create(RandomOrgService.class);

        try {
            final RandomOrgBlobRequest randomOrgBlobRequest = new RandomOrgBlobRequest(new RandomOrgBlobRequest.Params(API_KEY, 1, ENTROPY_SEED_LENGTH_BIT));

            Response<String> response = service.getRandom(createHeaderMap(), randomOrgBlobRequest).execute();
            if (response != null && response.isSuccessful() && response.body() != null) {
                String rawResponse = response.body();
                RandomOrgBlobResponse orgBlobResponse = new Gson().fromJson(rawResponse, RandomOrgBlobResponse.class);

                if (orgBlobResponse.id != randomOrgBlobRequest.id) {
                    throw new IllegalArgumentException("json rpc id do not match");
                }

                if (!RandomOrgUtil.base64EncodedSha512(API_KEY).equals(orgBlobResponse.result.random.hashedApiKey)) {
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

                return new Result(new Base64().decode(orgBlobResponse.result.random.data[0]), orgBlobResponse, System.currentTimeMillis() - startTime);
            }

        } catch (UnknownHostException e) {
            error = e;
            errMsg = "Cannot resolve host. Is the device offline?";
        } catch (Exception e) {
            error = e;
            errMsg = "Error during http request: " + e.getMessage();
        }

        return new Result(error, errMsg);
    }

    private Map<String, String> createHeaderMap() {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", USER_AGENT);
        return headers;
    }

    public static class Result {
        public final byte[] seed;
        public final RandomOrgBlobResponse response;
        public final long durationMs;
        public final Throwable throwable;
        public final String errorMsg;

        public Result(byte[] seed, RandomOrgBlobResponse response, long durationMs) {
            this.seed = seed;
            this.durationMs = durationMs;
            this.response = response;
            this.throwable = null;
            this.errorMsg = null;
        }

        public Result(Throwable t, String errorMsg) {
            this.durationMs = 0;
            this.response = null;
            this.seed = null;
            this.throwable = t;
            this.errorMsg = errorMsg;
        }

        public boolean isError() {
            return seed == null;
        }
    }
}
