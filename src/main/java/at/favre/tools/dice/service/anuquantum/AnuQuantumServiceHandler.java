package at.favre.tools.dice.service.anuquantum;

import at.favre.tools.dice.service.AServiceHandler;
import at.favre.tools.dice.util.ByteUtils;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.UnknownHostException;

public class AnuQuantumServiceHandler extends AServiceHandler {
    final static int ENTROPY_SEED_LENGTH_BYTE = 24;

    public AnuQuantumServiceHandler(boolean debug) {
        super(debug);
    }

    @Override
    public Result<AnuQuantomResponse> getRandom() {
        long startTime = System.currentTimeMillis();

        OkHttpClient client = createClient();
        Exception error = null;
        String errMsg = null;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://qrng.anu.edu.au/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        AnuQuantumService service = retrofit.create(AnuQuantumService.class);

        try {
            Response<AnuQuantomResponse> response = service.getRandom(createHeaderMap(), ENTROPY_SEED_LENGTH_BYTE).execute();
            if (response != null && response.isSuccessful() && response.body() != null) {
                byte[] rawResponse = ByteUtils.hexToBytes(response.body().data.get(0));
                return new Result<>(rawResponse, response.body(), System.currentTimeMillis() - startTime);
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
