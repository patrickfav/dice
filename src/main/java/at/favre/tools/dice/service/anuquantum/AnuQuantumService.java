package at.favre.tools.dice.service.anuquantum;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Query;

import java.util.Map;

public interface AnuQuantumService {
    @Headers({"DNT: 1"})
    @GET("/API/jsonI.php?length=1&type=hex16")
    Call<AnuQuantumResponse> getRandom(@HeaderMap Map<String, String> headers, @Query("size") int byteLength);
}
