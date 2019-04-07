package at.favre.tools.dice.service.randomorg;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RandomOrgUtilTest {

    //INFORMATION: {"jsonrpc":"2.0","result":{"random":{"method":"generateSignedBlobs","hashedApiKey":"c79p3+jyxhJPzAFSqsWQfmQnPaSazodEPIYD1gktjajfCfKeKN7O4miE2F/8rTc/ol3AowJUqaB296oHXEcudg==","n":1,"size":192,"format":"base64","data":["fR4w+uQBtriQhrjTJF5saw8gW18Oh7wA"],"completionTime":"2017-08-19 12:51:05Z","serialNumber":213},"signature":"Sw5GhRmUjDLjKWvQP4OowbA02niahXtA9bkG5WkUtx47O4lKrXLo0OCna7nLH/cPfl6UX/KwnnVMByxPtkTZxleaP6DZbJeWCyh/xRUBFjCX6FTgEF3ixE5FRm95CzXyq/wN6cd7sSpt0hlLvnahQgB76WMI/Wyhs2C6z6IZ0EbkdExlsl47dBCFo+N0WmPSjReQEN0UZGxp/QlsCbSAtWC2o9MbBKMydeXdQoFwO9CZ4Nh6CfaNxcHhHOM8kVYGObpLPZMsdSeXjFB+2xJd4TrokJOVTTcbSTv7HBqD3sWuRuEiDUwWV65D6QK+hYZjZgIYO/pAeou/ygvv4+526mBk7aKiwFaIjmIFLvx6tDSvMeJq/Lo+oiILd0u41jDs2LlxxxVOuRX1MgHYJWYi8O4snoIHUx9OULsn5NOtZdnrzN/YaUVDDia2WtbIGoJNeoFVo3Z1oIXzTnYDheTn/o5sRPgPneXJuFcbss8uFguVG52MO9kCLHWWwhVlA9mIckwhG26swlLVmbNbsDbBVdkyu3Um5+fDtcwBvttUf0CJahToY4gjNZeHA/85B/5E8/PUtFDUiCqCkmkWlQWGU+Pv4hWZOO21gUCqgog0zrRcQrXQpQ+w/NxCrwFbRNDYEfe4tlF99kTsPe0oAQ3pP1CGvspyEWN7yREvGHP9m9o=","bitsUsed":192,"bitsLeft":245904,"requestsLeft":977,"advisoryDelay":130},"id":-821824532}

    @Test
    public void verifySignature() {
        boolean verifyTrue = RandomOrgUtil.verifySignature("{\"jsonrpc\":\"2.0\",\"result\":{\"random\":{\"method\":\"generateSignedBlobs\",\"hashedApiKey\":\"c79p3+jyxhJPzAFSqsWQfmQnPaSazodEPIYD1gktjajfCfKeKN7O4miE2F/8rTc/ol3AowJUqaB296oHXEcudg==\",\"n\":1,\"size\":192,\"format\":\"base64\",\"data\":[\"fR4w+uQBtriQhrjTJF5saw8gW18Oh7wA\"],\"completionTime\":\"2017-08-19 12:51:05Z\",\"serialNumber\":213},\"signature\":\"Sw5GhRmUjDLjKWvQP4OowbA02niahXtA9bkG5WkUtx47O4lKrXLo0OCna7nLH/cPfl6UX/KwnnVMByxPtkTZxleaP6DZbJeWCyh/xRUBFjCX6FTgEF3ixE5FRm95CzXyq/wN6cd7sSpt0hlLvnahQgB76WMI/Wyhs2C6z6IZ0EbkdExlsl47dBCFo+N0WmPSjReQEN0UZGxp/QlsCbSAtWC2o9MbBKMydeXdQoFwO9CZ4Nh6CfaNxcHhHOM8kVYGObpLPZMsdSeXjFB+2xJd4TrokJOVTTcbSTv7HBqD3sWuRuEiDUwWV65D6QK+hYZjZgIYO/pAeou/ygvv4+526mBk7aKiwFaIjmIFLvx6tDSvMeJq/Lo+oiILd0u41jDs2LlxxxVOuRX1MgHYJWYi8O4snoIHUx9OULsn5NOtZdnrzN/YaUVDDia2WtbIGoJNeoFVo3Z1oIXzTnYDheTn/o5sRPgPneXJuFcbss8uFguVG52MO9kCLHWWwhVlA9mIckwhG26swlLVmbNbsDbBVdkyu3Um5+fDtcwBvttUf0CJahToY4gjNZeHA/85B/5E8/PUtFDUiCqCkmkWlQWGU+Pv4hWZOO21gUCqgog0zrRcQrXQpQ+w/NxCrwFbRNDYEfe4tlF99kTsPe0oAQ3pP1CGvspyEWN7yREvGHP9m9o=\",\"bitsUsed\":192,\"bitsLeft\":245904,\"requestsLeft\":977,\"advisoryDelay\":130},\"id\":-821824532}",
                "Sw5GhRmUjDLjKWvQP4OowbA02niahXtA9bkG5WkUtx47O4lKrXLo0OCna7nLH/cPfl6UX/KwnnVMByxPtkTZxleaP6DZbJeWCyh/xRUBFjCX6FTgEF3ixE5FRm95CzXyq/wN6cd7sSpt0hlLvnahQgB76WMI/Wyhs2C6z6IZ0EbkdExlsl47dBCFo+N0WmPSjReQEN0UZGxp/QlsCbSAtWC2o9MbBKMydeXdQoFwO9CZ4Nh6CfaNxcHhHOM8kVYGObpLPZMsdSeXjFB+2xJd4TrokJOVTTcbSTv7HBqD3sWuRuEiDUwWV65D6QK+hYZjZgIYO/pAeou/ygvv4+526mBk7aKiwFaIjmIFLvx6tDSvMeJq/Lo+oiILd0u41jDs2LlxxxVOuRX1MgHYJWYi8O4snoIHUx9OULsn5NOtZdnrzN/YaUVDDia2WtbIGoJNeoFVo3Z1oIXzTnYDheTn/o5sRPgPneXJuFcbss8uFguVG52MO9kCLHWWwhVlA9mIckwhG26swlLVmbNbsDbBVdkyu3Um5+fDtcwBvttUf0CJahToY4gjNZeHA/85B/5E8/PUtFDUiCqCkmkWlQWGU+Pv4hWZOO21gUCqgog0zrRcQrXQpQ+w/NxCrwFbRNDYEfe4tlF99kTsPe0oAQ3pP1CGvspyEWN7yREvGHP9m9o=");

        assertTrue(verifyTrue);
    }

    @Test
    public void verifySignatureV2() {
        boolean verifyTrue = RandomOrgUtil.verifySignature("{\"jsonrpc\":\"2.0\",\"result\":{\"random\":{\"method\":\"generateSignedBlobs\",\"hashedApiKey\":\"c79p3+jyxhJPzAFSqsWQfmQnPaSazodEPIYD1gktjajfCfKeKN7O4miE2F/8rTc/ol3AowJUqaB296oHXEcudg==\",\"n\":1,\"size\":192,\"format\":\"base64\",\"data\":[\"NvILrgnT1DcfvREG+vsyHfQBlCuEnjmH\"],\"license\":{\"type\":\"beta\",\"text\":\"These values are licensed for non-profit, commercial (non-gambling), and for gambling purposes with no prize value limitations.\",\"infoUrl\":\"https://api.random.org/licenses/beta\"},\"userData\":null,\"completionTime\":\"2019-04-07 15:43:08Z\",\"serialNumber\":1603},\"signature\":\"Hydzyx6ddlItpRvYhVd1K3k4koM3VL+A7DPcb3POptSJEDAJZYio9D1yRPINUoz53PEB+hbSrsVty4ihQWkULXsOjfTp+p2vxHH5KXMYD5gHxZVYhDU5qg6Tm48lmQMecY6MGA4OTrES6NzVhWRGzDmCNbPTc5PfAbnPwIMd9tY3OxG2km1NQpUgvAYessKKtFZSEZGI0vRWUeEgKFExyKuEXzxp2xydM6MXBoO0PaYZOh40E/9EYh0t89u6vIWRfhkfScKaO3bG/kzNDsI6GNueovtcuoBStZ/oGidZhziBos6SR8SUqGdQPIs65+WwyY3XPpzdtCVNL1BLdsBJ4Ue8mDKhLlIxzogeDENip7oVuRqEDTEaW+V7O0TOz6ogywlsyL9xXT8P1FnBUqNnP/WxiBvmME8/grGovf3+Aowo2B2XAJFG+cYLgsPyfptlxwKNktb1f5JGO81HdzLWGfx96ZkUbA7EsURAzUZLasrm77pim+wZj7CoKJgs4Al01Z2JndVgtlgMsPUwKpkJ3nQCz+RNRM/Zr9uxCDU6wdoKICmQKEzFkvoMZc/fdafZvD7wy/5OeXVUJYaObbcWQ9OsGicQNk+yg0Twv/kez+1sOjrV38YNj4xKsnOYHT+2Po7EqMyQlxtZ2nD1pgEqVslOSIZ3th5htffHCxTzWQ4=\",\"bitsUsed\":192,\"bitsLeft\":249232,\"requestsLeft\":996,\"advisoryDelay\":880},\"id\":-1017564374}", "Hydzyx6ddlItpRvYhVd1K3k4koM3VL+A7DPcb3POptSJEDAJZYio9D1yRPINUoz53PEB+hbSrsVty4ihQWkULXsOjfTp+p2vxHH5KXMYD5gHxZVYhDU5qg6Tm48lmQMecY6MGA4OTrES6NzVhWRGzDmCNbPTc5PfAbnPwIMd9tY3OxG2km1NQpUgvAYessKKtFZSEZGI0vRWUeEgKFExyKuEXzxp2xydM6MXBoO0PaYZOh40E/9EYh0t89u6vIWRfhkfScKaO3bG/kzNDsI6GNueovtcuoBStZ/oGidZhziBos6SR8SUqGdQPIs65+WwyY3XPpzdtCVNL1BLdsBJ4Ue8mDKhLlIxzogeDENip7oVuRqEDTEaW+V7O0TOz6ogywlsyL9xXT8P1FnBUqNnP/WxiBvmME8/grGovf3+Aowo2B2XAJFG+cYLgsPyfptlxwKNktb1f5JGO81HdzLWGfx96ZkUbA7EsURAzUZLasrm77pim+wZj7CoKJgs4Al01Z2JndVgtlgMsPUwKpkJ3nQCz+RNRM/Zr9uxCDU6wdoKICmQKEzFkvoMZc/fdafZvD7wy/5OeXVUJYaObbcWQ9OsGicQNk+yg0Twv/kez+1sOjrV38YNj4xKsnOYHT+2Po7EqMyQlxtZ2nD1pgEqVslOSIZ3th5htffHCxTzWQ4=");

        assertTrue(verifyTrue);
    }

    @Test
    public void verifyRSA() throws Exception {
        boolean verifyTrue = RandomOrgUtil.verifyRSA(RandomOrgUtil.parsePublicKey(new Base64().decode(RandomOrgUtil.RANDOM_ORG_PUB_KEY)),
                "{\"method\":\"generateSignedBlobs\",\"hashedApiKey\":\"c79p3+jyxhJPzAFSqsWQfmQnPaSazodEPIYD1gktjajfCfKeKN7O4miE2F/8rTc/ol3AowJUqaB296oHXEcudg==\",\"n\":1,\"size\":192,\"format\":\"base64\",\"data\":[\"fR4w+uQBtriQhrjTJF5saw8gW18Oh7wA\"],\"completionTime\":\"2017-08-19 12:51:05Z\",\"serialNumber\":213}".getBytes(StandardCharsets.UTF_8),
                new Base64().decode("Sw5GhRmUjDLjKWvQP4OowbA02niahXtA9bkG5WkUtx47O4lKrXLo0OCna7nLH/cPfl6UX/KwnnVMByxPtkTZxleaP6DZbJeWCyh/xRUBFjCX6FTgEF3ixE5FRm95CzXyq/wN6cd7sSpt0hlLvnahQgB76WMI/Wyhs2C6z6IZ0EbkdExlsl47dBCFo+N0WmPSjReQEN0UZGxp/QlsCbSAtWC2o9MbBKMydeXdQoFwO9CZ4Nh6CfaNxcHhHOM8kVYGObpLPZMsdSeXjFB+2xJd4TrokJOVTTcbSTv7HBqD3sWuRuEiDUwWV65D6QK+hYZjZgIYO/pAeou/ygvv4+526mBk7aKiwFaIjmIFLvx6tDSvMeJq/Lo+oiILd0u41jDs2LlxxxVOuRX1MgHYJWYi8O4snoIHUx9OULsn5NOtZdnrzN/YaUVDDia2WtbIGoJNeoFVo3Z1oIXzTnYDheTn/o5sRPgPneXJuFcbss8uFguVG52MO9kCLHWWwhVlA9mIckwhG26swlLVmbNbsDbBVdkyu3Um5+fDtcwBvttUf0CJahToY4gjNZeHA/85B/5E8/PUtFDUiCqCkmkWlQWGU+Pv4hWZOO21gUCqgog0zrRcQrXQpQ+w/NxCrwFbRNDYEfe4tlF99kTsPe0oAQ3pP1CGvspyEWN7yREvGHP9m9o="));
        assertTrue(verifyTrue);
    }

    @Test
    public void parsePublicKeyAndTestEncrypt() throws Exception {
        PublicKey publicKey = RandomOrgUtil.parsePublicKey(new Base64().decode(RandomOrgUtil.RANDOM_ORG_PUB_KEY));

        byte[] plain = new byte[]{0, 1, 4, 2, 1, 2, 3, 4, 2};
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypted = c.doFinal(new byte[]{0, 1, 4, 2, 1, 2, 3, 4, 2});
        assertFalse(Arrays.equals(plain, encrypted));

    }
}
