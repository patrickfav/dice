package at.favre.tools.dice.service;

import at.favre.tools.dice.service.model.RandomOrgBlobRequest;
import at.favre.tools.dice.service.model.RandomOrgBlobResponse;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.codec.binary.Base64;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */
public class RandomOrgServiceHandler {
    final static int ENTROPY_SEED_LENGTH_BIT = 192;
    private final boolean debug;

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
            client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
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
            Response<RandomOrgBlobResponse> response = service.getRandom(new RandomOrgBlobRequest(new RandomOrgBlobRequest.Params(API_KEY, 1, ENTROPY_SEED_LENGTH_BIT))).execute();

            if (response != null && response.isSuccessful() && response.body() != null) {
                return new Result(new Base64().decode(response.body().result.random.data[0]), System.currentTimeMillis() - startTime);
            }

        } catch (Exception e) {
            error = e;
            errMsg = "Error during http request: " + e.getMessage();
        }

        return new Result(error, errMsg);
    }

    public static class Result {
        public final byte[] seed;
        public final long durationMs;
        public final Throwable t;
        public final String errorMsg;

        public Result(byte[] seed, long durationMs) {
            this.seed = seed;
            this.durationMs = durationMs;
            this.t = null;
            this.errorMsg = null;
        }

        public Result(Throwable t, String errorMsg) {
            this.durationMs = 0;
            this.seed = null;
            this.t = t;
            this.errorMsg = errorMsg;
        }

        public boolean isError() {
            return seed == null;
        }
    }
}
