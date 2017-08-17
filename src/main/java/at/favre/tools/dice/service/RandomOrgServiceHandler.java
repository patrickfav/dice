package at.favre.tools.dice.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.codec.binary.Base64;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */
public class RandomOrgServiceHandler {

    private final boolean debug;

    public RandomOrgServiceHandler(boolean debug) {
        this.debug = debug;
    }

    /*
        It will be possible to convert beta keys production keys before the beta ends on 31 December 2017.
         */
    private static final String API_KEY = "ae169728-dec5-4771-b2e8-cc1801aaad70";

    public byte[] getRandom() {
        OkHttpClient client;
        if(debug) {
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
            Response<RandomOrgBlobResponse> response = service.getRandom(new RandomOrgBlobRequest(new RandomOrgBlobRequest.Params(API_KEY, 1, 128))).execute();

            if (response.isSuccessful() && response.body() != null) {
                return new Base64().decode(response.body().result.random.data[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
