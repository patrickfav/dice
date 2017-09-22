package at.favre.tools.dice.service;

import at.favre.tools.dice.RndTool;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.HashMap;
import java.util.Map;

public abstract class AServiceHandler<T> implements ServiceHandler<T> {
    private final static String USER_AGENT = "dice/" + RndTool.jarVersion() + " (" + System.getProperty("os.name") + "; Java " + System.getProperty("java.version") + ") github.com/patrickfav/dice";
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
        if (debug) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            return new OkHttpClient.Builder().addNetworkInterceptor(interceptor).addInterceptor(interceptor).build();
        } else {
            return new OkHttpClient.Builder().build();
        }
    }

}
