package at.favre.tools.dice.service.hotbits;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.util.Map;

public interface HotbitsService {
    @Headers({"DNT: 1"})
    @GET("/cgi-bin/Hotbits?fmt=bin")
    Call<ResponseBody> getRandom(@HeaderMap Map<String, String> headers, @Query("nbytes") int bitLength, @Query("apikey") String apiKey);
}
