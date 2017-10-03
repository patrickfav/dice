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

import io.reactivex.Single;

public interface ServiceHandler<T> {

    /**
     * Synchronously gets random data from an external service
     *
     * @return the result of the request
     */
    Result<T> getRandom();

    /**
     * Get the request as Observable
     *
     * @return rxjava single
     */
    Single<Result<T>> asObservable();

    /**
     * Get the user friendly and readable name for this service
     *
     * @return name
     */
    String getName();

    /**
     * Wrapper class for the response of the external object
     *
     * @param <T>
     */
    class Result<T> {
        public final String serviceName;
        public final byte[] seed;
        public final T response;
        public final long durationMs;
        public final Throwable throwable;
        public final String errorMsg;

        public Result(String serviceName, byte[] seed, T response, long durationMs) {
            this.serviceName = serviceName;
            this.seed = seed;
            this.durationMs = durationMs;
            this.response = response;
            this.throwable = null;
            this.errorMsg = null;
        }

        public Result(String serviceName, Throwable t, String errorMsg) {
            this.serviceName = serviceName;
            this.durationMs = 0;
            this.response = null;
            this.seed = null;
            this.throwable = t;
            this.errorMsg = errorMsg;
        }

        public boolean isError() {
            return seed == null;
        }
    }
}
