package at.favre.tools.dice.service;

import at.favre.tools.dice.service.model.RandomOrgBlobRequest;
import at.favre.tools.dice.service.model.RandomOrgBlobResponse;
import at.favre.tools.dice.util.SecurityUtil;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.codec.binary.Base64;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */
public class RandomOrgServiceHandler {
    final static int ENTROPY_SEED_LENGTH_BIT = 192;
    private final boolean debug;

    public RandomOrgServiceHandler(boolean debug) {
        this.debug = debug;
    }

    /*
     * It will be possible to convert beta keys production keys before the beta ends on 31 December 2017.
     */
    private static final String API_KEY = "ae169728-dec5-4771-b2e8-cc1801aaad70";

    public Result getRandom() {
        long startTime = System.currentTimeMillis();

        OkHttpClient client;
        Exception error = null;
        String errMsg = null;
        if (debug) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        } else {
            client = new OkHttpClient.Builder().build();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.random.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        RandomOrgService service = retrofit.create(RandomOrgService.class);

        try {
            final RandomOrgBlobRequest randomOrgBlobRequest = new RandomOrgBlobRequest(new RandomOrgBlobRequest.Params(API_KEY, 1, ENTROPY_SEED_LENGTH_BIT));

            Response<RandomOrgBlobResponse> response = service.getRandom(randomOrgBlobRequest).execute();

            if (response != null && response.isSuccessful() && response.body() != null) {
                RandomOrgBlobResponse orgBlobResponse = response.body();

                if (orgBlobResponse.id != randomOrgBlobRequest.id) {
                    throw new IllegalArgumentException("json rpc id do not match");
                }

                if (!SecurityUtil.base64EncodedSha512(API_KEY).equals(orgBlobResponse.result.random.hashedApiKey)) {
                    throw new IllegalArgumentException("used api key does not match");
                }

                return new Result(new Base64().decode(orgBlobResponse.result.random.data[0]), System.currentTimeMillis() - startTime);
            }

        } catch (Exception e) {
            error = e;
            errMsg = "Error during http request: " + e.getMessage();
        }

        return new Result(error, errMsg);
    }

    public static class Result {
        public final byte[] seed;
        public final long durationMs;
        public final Throwable t;
        public final String errorMsg;

        public Result(byte[] seed, long durationMs) {
            this.seed = seed;
            this.durationMs = durationMs;
            this.t = null;
            this.errorMsg = null;
        }

        public Result(Throwable t, String errorMsg) {
            this.durationMs = 0;
            this.seed = null;
            this.t = t;
            this.errorMsg = errorMsg;
        }

        public boolean isError() {
            return seed == null;
        }
    }


    public static final String RANDOM_ORG_PUB_KEY =
            "MIIIqjCCB5KgAwIBAgIQC4lCuW7RYqlTYGF+/nJQaDANBgkqhkiG9w0BAQsFADB1\n" +
                    "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMRkwFwYDVQQLExB3\n" +
                    "d3cuZGlnaWNlcnQuY29tMTQwMgYDVQQDEytEaWdpQ2VydCBTSEEyIEV4dGVuZGVk\n" +
                    "IFZhbGlkYXRpb24gU2VydmVyIENBMB4XDTE3MDgxMDAwMDAwMFoXDTE5MTAxNzEy\n" +
                    "MDAwMFowgbUxHTAbBgNVBA8MFFByaXZhdGUgT3JnYW5pemF0aW9uMRMwEQYLKwYB\n" +
                    "BAGCNzwCAQMTAklFMQ8wDQYDVQQFEwY0ODk0MzQxCzAJBgNVBAYTAklFMQ8wDQYD\n" +
                    "VQQHEwZEdWJsaW4xOzA5BgNVBAoTMlJBTkRPTS5PUkcgKFJhbmRvbW5lc3MgYW5k\n" +
                    "IEludGVncml0eSBTZXJ2aWNlcyBMdGQpMRMwEQYDVQQDEwpSQU5ET00uT1JHMIIC\n" +
                    "IjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEA7O3HQWLnTzCCj/qwoI4vj/T9\n" +
                    "237we74rwcJW2w4SuzIKVlAn5yhaJcaeQpdpmHwmQt3aU8G1ba7n3xl7hdePkh+a\n" +
                    "EkYM3iVOhJZdkCKjzw2x7lUSQInZksgns8R4iGklJPInX6fmBjErt1YrjI8B5Hqz\n" +
                    "3koibkqIZgVuZ1QfJogbmsrT64imgiDdeG3XDcOY4yDzS734bNqRUNYha3aDnwvx\n" +
                    "rubyMhfWtBl2y6nXKDbeMKJ9NWu723V7L+BGFeEvYMPq8ieRVJ7ycavKeSXEoi9G\n" +
                    "vgzCjuy2GBJOXs41O5f07VnqGxci6uqyblEgr0SoNETYFnJsSVkryyTPtO7lh5jd\n" +
                    "Fg4QmHBUEfzfcWQMkxj4LbDvRHMn5UIrofkA7g+97Wf/IQnZzhlZh+DgIb3jjXD5\n" +
                    "0GqJsd7cd0ojJZuzGf6BLSZ8g2KZOJ3KtB1u/nZ4HVQUdP6ZNop3mEx7Miar7wSD\n" +
                    "jRzGg4ayfxHa8pOtE6o8pe0d7lVu3XTHC9kL5qZ3Xqld6Sx9tJ2ZQ2oDjTPlPIhY\n" +
                    "GMLdeEhXmYUrhnDChpOJrWvsb/eh4M38sWUccBQTl9sBvWRkrbSCazlxZA+Y5KOP\n" +
                    "EJ3NIR8GjKFNwbd8Bk9Yk3LnbocSp3E82BVD1gi4zRd9MtBhClGc//xi8S5WrFho\n" +
                    "8l+sZ+dCq/iuVYLTkGUCAwEAAaOCA/MwggPvMB8GA1UdIwQYMBaAFD3TUKXWoK3u\n" +
                    "80pgCmXTIdT4+NYPMB0GA1UdDgQWBBQYtotLM0y6R+J/uejV3c4bl059BzAlBgNV\n" +
                    "HREEHjAcggpSQU5ET00uT1JHgg53d3cuUkFORE9NLk9SRzAOBgNVHQ8BAf8EBAMC\n" +
                    "BaAwHQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMHUGA1UdHwRuMGwwNKAy\n" +
                    "oDCGLmh0dHA6Ly9jcmwzLmRpZ2ljZXJ0LmNvbS9zaGEyLWV2LXNlcnZlci1nMi5j\n" +
                    "cmwwNKAyoDCGLmh0dHA6Ly9jcmw0LmRpZ2ljZXJ0LmNvbS9zaGEyLWV2LXNlcnZl\n" +
                    "ci1nMi5jcmwwSwYDVR0gBEQwQjA3BglghkgBhv1sAgEwKjAoBggrBgEFBQcCARYc\n" +
                    "aHR0cHM6Ly93d3cuZGlnaWNlcnQuY29tL0NQUzAHBgVngQwBATCBiAYIKwYBBQUH\n" +
                    "AQEEfDB6MCQGCCsGAQUFBzABhhhodHRwOi8vb2NzcC5kaWdpY2VydC5jb20wUgYI\n" +
                    "KwYBBQUHMAKGRmh0dHA6Ly9jYWNlcnRzLmRpZ2ljZXJ0LmNvbS9EaWdpQ2VydFNI\n" +
                    "QTJFeHRlbmRlZFZhbGlkYXRpb25TZXJ2ZXJDQS5jcnQwDAYDVR0TAQH/BAIwADCC\n" +
                    "AfgGCisGAQQB1nkCBAIEggHoBIIB5AHiAHUApLkJkLQYWBSHuxOizGdwCjw1mAT5\n" +
                    "G9+443fNDsgN3BAAAAFdyx1MsgAABAMARjBEAh8IyfI5Vt8N/uZCHlWTJHOrHxFl\n" +
                    "V2RjbELyjlesvl6jAiEAnylbj8iYFyfYOiFNqGeX8Z6PoIdmaIf3/cwwoQkGvpsA\n" +
                    "dwBWFAaaL9fC7NP14b1Esj7HRna5vJkRXMDvlJhV1onQ3QAAAV3LHU2LAAAEAwBI\n" +
                    "MEYCIQDxHIjGncPJVA2SwIjf/i4JR5ZAeiQf/aHEStus7/TOUQIhAKrBV6Z8dLnY\n" +
                    "DYVkZyUzUHVrK2YFV89qYTu4UPxzCXC1AHcA7ku9t3XOYLrhQmkfq+GeZqMPfl+w\n" +
                    "ctiDAMR7iXqo/csAAAFdyx1M9AAABAMASDBGAiEAw4ROp417GjHRGSsAbzYMZ8jK\n" +
                    "e7UlGShXIcCQh0rEoDECIQCr3bq1v+cN5n8E/kWpjAdVE5dWLy027E0UqTPffL1x\n" +
                    "LgB3ALvZ37wfinG1k5Qjl6qSe0c4V5UKq1LoGpCWZDaOHtGFAAABXcsdTXMAAAQD\n" +
                    "AEgwRgIhAKU9ujQIANOXUF+l5Ss1nyQ8VifDq+q/2KAETa+QeTdJAiEAx6GB7SZu\n" +
                    "ORhtQURdzvfLTBphnRosLobuf70wjB2WHikwDQYJKoZIhvcNAQELBQADggEBANTX\n" +
                    "QYPeDIVmiWwKOztrlzrHQ7peHGPR6Uu1W5bmXitfCaXoJlRONCazf1n32uzhtgQI\n" +
                    "QQJ+Iw/4yOJ2zvpms4Ta4kBCjz3eMnZpZ7Spw9dD7WHYNY8E2p1pZFLpSqM8VYFj\n" +
                    "YqFHDRhx0LszfVsnfa0A0R+rLlodvr/zT6blLzbbtpnS4hDyUipwkNaZcatRPTFi\n" +
                    "6eFFeAhzbc1qmWIGLqploCN81S9wQH/MgAShP6/xBibxJw7ZPiM7x4tURiQEmyXy\n" +
                    "mmBqVyf06iwB8GTamTNiESHnzyXRgzi6pG9ZeSm9h1YiJ35pMxie1babZ6W3Ordd\n" +
                    "0IoUwj83Z0XukGs7CFA=\n";
}
