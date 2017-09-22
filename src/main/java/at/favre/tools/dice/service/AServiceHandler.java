package at.favre.tools.dice.service;

import at.favre.tools.dice.RndTool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.HashMap;
import java.util.Map;

public class AServiceHandler {
    private final static String USER_AGENT = "dice/" + RndTool.jarVersion() + " (" + System.getProperty("os.name") + "; Java " + System.getProperty("java.version") + ") github.com/patrickfav/dice";
    protected final boolean debug;

    protected AServiceHandler(boolean debug) {
        this.debug = debug;
    }

    protected Map<String, String> createHeaderMap() {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", USER_AGENT);
        return headers;
    }

    protected OkHttpClient createClient() {
        if (debug) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return new OkHttpClient.Builder().addNetworkInterceptor(interceptor).addInterceptor(interceptor).build();
        } else {
            return new OkHttpClient.Builder().build();
        }
    }

    public static class Result<T> {
        public final byte[] seed;
        public final T response;
        public final long durationMs;
        public final Throwable throwable;
        public final String errorMsg;

        public Result(byte[] seed, T response, long durationMs) {
            this.seed = seed;
            this.durationMs = durationMs;
            this.response = response;
            this.throwable = null;
            this.errorMsg = null;
        }

        public Result(Throwable t, String errorMsg) {
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
