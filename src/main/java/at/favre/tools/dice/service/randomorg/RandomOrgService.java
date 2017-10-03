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

import at.favre.tools.dice.service.randomorg.model.RandomOrgBlobRequest;
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