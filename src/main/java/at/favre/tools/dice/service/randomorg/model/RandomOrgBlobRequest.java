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

package at.favre.tools.dice.service.randomorg.model;

import java.util.Random;

public final class RandomOrgBlobRequest {

    public final String jsonrpc = "2.0";
    public final String method = "generateSignedBlobs";
    public final int id;
    public final Params params;

    public RandomOrgBlobRequest(Params params) {
        this(new Random().nextInt(), params);
    }

    public RandomOrgBlobRequest(int id, Params params) {
        this.id = id;
        this.params = params;
    }

    public static class Params {
        public final String apiKey; //Your API key, which is used to track the true random bit usage for your client.
        public final int n; //How many random blobs you need.
        public final int size; //The size of each blob, measured in bits.
        public final String format = "base64";

        public Params(String apiKey, int n, int size) {
            this.apiKey = apiKey;
            this.n = n;
            this.size = size;
        }
    }
}

//{
//        "jsonrpc": "2.0",
//        "method": "myMethod",
//        "params": {
//        ...
//        },
//        "id": 42
//        }


//    The following parameters are mandatory and should be specified in the params array of the JSON-RPC request:
//
//        c
//        Your API key, which is used to track the true random bit usage for your client.
//        n
//        How many random blobs you need. Must be within the [1,100] range.
//        size
//        The size of each blob, measured in bits. Must be within the [1,1048576] range and must be divisible by 8.
//        The total size of all blobs requested must not exceed 1,048,576 bits (128 KiB).