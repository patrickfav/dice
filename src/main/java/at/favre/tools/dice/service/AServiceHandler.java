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

package at.favre.tools.dice.service;

import at.favre.tools.dice.util.MiscUtil;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public abstract class AServiceHandler<T> implements ServiceHandler<T> {
    private static final String USER_AGENT = "dice/" + MiscUtil.jarVersion() + " (" + System.getProperty("os.name") + "; Java " + System.getProperty("java.version") + ") github.com/patrickfav/dice";
    protected final boolean debug;

    protected AServiceHandler(boolean debug) {
        this.debug = debug;
    }

    @Override
    public Single<Result<T>> asObservable() {
        return Single.fromCallable(this::getRandom);
    }

    protected Map<String, String> createHeaderMap() {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", USER_AGENT);
        return headers;
    }

    protected OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS);
        if (debug) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return builder
                    .addNetworkInterceptor(interceptor)
                    .addInterceptor(interceptor)
                    .build();
        } else {
            return builder.build();
        }
    }

}
