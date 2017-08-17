package at.favre.tools.dice.service;

import java.util.Arrays;

public final class RandomOrgBlobResponse {

    public final String jsonrpc;
    public final int id;
    public final Result result;

    public RandomOrgBlobResponse(String jsonrpc, int id, Result result) {
        this.jsonrpc = jsonrpc;
        this.id = id;
        this.result = result;
    }

    public static class Result {
        public final Random random;
        public final int bitsUsed; //An integer containing the number of true random bits used to complete this request.
        public final int bitsLeft; //An integer containing the (estimated) number of remaining true random bits available to the client.
        public final int requestsLeft; //An integer containing the (estimated) number of remaining API requests available to the client.
        public final int advisoryDelay; //An integer containing the recommended number of milliseconds that the client should delay before issuing another request.

        public Result(Random random, int bitsUsed, int bitsLeft, int requestsLeft, int advisoryDelay) {
            this.random = random;
            this.bitsUsed = bitsUsed;
            this.bitsLeft = bitsLeft;
            this.requestsLeft = requestsLeft;
            this.advisoryDelay = advisoryDelay;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Result result = (Result) o;

            if (bitsUsed != result.bitsUsed) return false;
            if (bitsLeft != result.bitsLeft) return false;
            if (requestsLeft != result.requestsLeft) return false;
            if (advisoryDelay != result.advisoryDelay) return false;
            return random != null ? random.equals(result.random) : result.random == null;
        }

        @Override
        public int hashCode() {
            int result = random != null ? random.hashCode() : 0;
            result = 31 * result + bitsUsed;
            result = 31 * result + bitsLeft;
            result = 31 * result + requestsLeft;
            result = 31 * result + advisoryDelay;
            return result;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "random=" + random +
                    ", bitsUsed=" + bitsUsed +
                    ", bitsLeft=" + bitsLeft +
                    ", requestsLeft=" + requestsLeft +
                    ", advisoryDelay=" + advisoryDelay +
                    '}';
        }
    }

    /**
     * This object encapsulates the random blobs and associated data. It contains the following properties.
     */
    public static class Random {
        public final String[] data; //An array containing the blobs requested. Each blob will be formatted as a string encoded in the format specified in the request.
        public final String completionTime; //A string containing the timestamp in ISO 8601 format at which the request was completed.

        public Random(String[] data, String completionTime) {
            this.data = data;
            this.completionTime = completionTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Random random = (Random) o;

            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(data, random.data)) return false;
            return completionTime != null ? completionTime.equals(random.completionTime) : random.completionTime == null;
        }

        @Override
        public int hashCode() {
            int result = Arrays.hashCode(data);
            result = 31 * result + (completionTime != null ? completionTime.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Random{" +
                    "data=" + Arrays.toString(data) +
                    ", completionTime='" + completionTime + '\'' +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RandomOrgBlobResponse that = (RandomOrgBlobResponse) o;

        if (id != that.id) return false;
        if (jsonrpc != null ? !jsonrpc.equals(that.jsonrpc) : that.jsonrpc != null) return false;
        return result != null ? result.equals(that.result) : that.result == null;
    }

    @Override
    public int hashCode() {
        int result1 = jsonrpc != null ? jsonrpc.hashCode() : 0;
        result1 = 31 * result1 + id;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return "RandomOrgBlobResponse{" +
                "jsonrpc='" + jsonrpc + '\'' +
                ", id=" + id +
                ", result=" + result +
                '}';
    }
}
