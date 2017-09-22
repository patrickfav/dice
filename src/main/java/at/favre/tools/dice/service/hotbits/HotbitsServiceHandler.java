package at.favre.tools.dice.service.hotbits;

import at.favre.tools.dice.service.AServiceHandler;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.net.UnknownHostException;

public class HotbitsServiceHandler extends AServiceHandler {
    final static int ENTROPY_SEED_LENGTH_BYTE = 24;

    /*
     * Your HotBits API Key is:
     */
    private static final String API_KEY = "HB1QEJzoHjD5sLx3kKYIsnxP3rM";

    public HotbitsServiceHandler(boolean debug) {
        super(debug);
    }

    public Result<Void> getRandom() {
        long startTime = System.currentTimeMillis();

        OkHttpClient client = createClient();
        Exception error = null;
        String errMsg = null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.fourmilab.ch/")
                .client(client)
                .build();

        HotbitsService service = retrofit.create(HotbitsService.class);

        try {
            Response<ResponseBody> response = service.getRandom(createHeaderMap(), ENTROPY_SEED_LENGTH_BYTE, API_KEY).execute();
            if (response != null && response.isSuccessful() && response.body() != null) {
                byte[] rawResponse = response.body().bytes();
                return new Result<>(rawResponse, null, System.currentTimeMillis() - startTime);
            }
        } catch (UnknownHostException e) {
            error = e;
            errMsg = "Cannot resolve host. Is the device offline?";
        } catch (Exception e) {
            error = e;
            errMsg = "Error during http request: " + e.getMessage();
        }

        return new Result<>(error, errMsg);
    }
}
