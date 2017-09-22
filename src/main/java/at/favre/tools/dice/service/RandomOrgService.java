package at.favre.tools.dice.service;

import at.favre.tools.dice.service.model.RandomOrgBlobRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.Map;

public interface RandomOrgService {
    @Headers({"DNT: 1"})
    @POST("/json-rpc/1/invoke")
    Call<String> getRandom(@HeaderMap Map<String, String> headers, @Body RandomOrgBlobRequest request);
}