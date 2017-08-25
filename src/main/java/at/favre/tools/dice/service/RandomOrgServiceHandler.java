package at.favre.tools.dice.service;

import at.favre.tools.dice.RndTool;
import at.favre.tools.dice.service.model.RandomOrgBlobRequest;
import at.favre.tools.dice.service.model.RandomOrgBlobResponse;
import at.favre.tools.dice.util.SecurityUtil;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.codec.binary.Base64;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 */
public class RandomOrgServiceHandler {
    final static int ENTROPY_SEED_LENGTH_BIT = 128;
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
            client = new OkHttpClient.Builder().addNetworkInterceptor(interceptor).build();
        } else {
            client = new OkHttpClient.Builder().build();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.random.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        RandomOrgService service = retrofit.create(RandomOrgService.class);

        try {
            final RandomOrgBlobRequest randomOrgBlobRequest = new RandomOrgBlobRequest(new RandomOrgBlobRequest.Params(API_KEY, 1, ENTROPY_SEED_LENGTH_BIT));

            Response<RandomOrgBlobResponse> response = service.getRandom(createHeaderMap(), randomOrgBlobRequest).execute();

            if (response != null && response.isSuccessful() && response.body() != null) {
                RandomOrgBlobResponse orgBlobResponse = response.body();

                if (orgBlobResponse.id != randomOrgBlobRequest.id) {
                    throw new IllegalArgumentException("json rpc id do not match");
                }

                if (!SecurityUtil.base64EncodedSha512(API_KEY).equals(orgBlobResponse.result.random.hashedApiKey)) {
                    throw new IllegalArgumentException("used api key does not match");
                }

                return new Result(new Base64().decode(orgBlobResponse.result.random.data[0]), orgBlobResponse, System.currentTimeMillis() - startTime);
            }

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

    public static final String RANDOM_ORG_PUB_KEY =
            "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";
}
