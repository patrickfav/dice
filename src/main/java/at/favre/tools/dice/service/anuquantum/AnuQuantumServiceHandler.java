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

package at.favre.tools.dice.service.anuquantum;

import at.favre.lib.bytes.Bytes;
import at.favre.tools.dice.service.AServiceHandler;
import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.UnknownHostException;

/**
 * ANU Quantum Random Numbers Server
 * Quantum true random number generator from Australian university
 * <p>
 * See https://qrng.anu.edu.au/
 */
public final class AnuQuantumServiceHandler extends AServiceHandler {
    final static int ENTROPY_SEED_LENGTH_BYTE = 24;

    public AnuQuantumServiceHandler(boolean debug) {
        super(debug);
    }

    @Override
    public Result<AnuQuantumResponse> getRandom() {
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
            Response<AnuQuantumResponse> response = service.getRandom(createHeaderMap(), ENTROPY_SEED_LENGTH_BYTE).execute();
            if (response != null && response.isSuccessful() && response.body() != null) {
                byte[] rawResponse = Bytes.parseHex(response.body().data.get(0)).array();
                return new Result<>(getName(), rawResponse, response.body(), System.currentTimeMillis() - startTime);
            }
        } catch (UnknownHostException e) {
            error = e;
            errMsg = "Cannot resolve host. Is the device offline?";
        } catch (Exception e) {
            error = e;
            errMsg = "Error during http request: " + e.getMessage();
        }

        return new Result<>(getName(), error, errMsg);
    }

    @Override
    public String getName() {
        return "ANU Quantum";
    }
}
