package at.favre.tools.dice.util;

import at.favre.tools.dice.service.RandomOrgServiceHandler;
import org.apache.commons.codec.binary.Base64;
import org.junit.Ignore;
import org.junit.Test;

import javax.crypto.Cipher;
import java.security.PublicKey;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SecurityUtilTest {

    //INFORMATION: {"jsonrpc":"2.0","result":{"random":{"method":"generateSignedBlobs","hashedApiKey":"c79p3+jyxhJPzAFSqsWQfmQnPaSazodEPIYD1gktjajfCfKeKN7O4miE2F/8rTc/ol3AowJUqaB296oHXEcudg==","n":1,"size":192,"format":"base64","data":["fR4w+uQBtriQhrjTJF5saw8gW18Oh7wA"],"completionTime":"2017-08-19 12:51:05Z","serialNumber":213},"signature":"Sw5GhRmUjDLjKWvQP4OowbA02niahXtA9bkG5WkUtx47O4lKrXLo0OCna7nLH/cPfl6UX/KwnnVMByxPtkTZxleaP6DZbJeWCyh/xRUBFjCX6FTgEF3ixE5FRm95CzXyq/wN6cd7sSpt0hlLvnahQgB76WMI/Wyhs2C6z6IZ0EbkdExlsl47dBCFo+N0WmPSjReQEN0UZGxp/QlsCbSAtWC2o9MbBKMydeXdQoFwO9CZ4Nh6CfaNxcHhHOM8kVYGObpLPZMsdSeXjFB+2xJd4TrokJOVTTcbSTv7HBqD3sWuRuEiDUwWV65D6QK+hYZjZgIYO/pAeou/ygvv4+526mBk7aKiwFaIjmIFLvx6tDSvMeJq/Lo+oiILd0u41jDs2LlxxxVOuRX1MgHYJWYi8O4snoIHUx9OULsn5NOtZdnrzN/YaUVDDia2WtbIGoJNeoFVo3Z1oIXzTnYDheTn/o5sRPgPneXJuFcbss8uFguVG52MO9kCLHWWwhVlA9mIckwhG26swlLVmbNbsDbBVdkyu3Um5+fDtcwBvttUf0CJahToY4gjNZeHA/85B/5E8/PUtFDUiCqCkmkWlQWGU+Pv4hWZOO21gUCqgog0zrRcQrXQpQ+w/NxCrwFbRNDYEfe4tlF99kTsPe0oAQ3pP1CGvspyEWN7yREvGHP9m9o=","bitsUsed":192,"bitsLeft":245904,"requestsLeft":977,"advisoryDelay":130},"id":-821824532}

    @Ignore
    @Test
    public void verify() throws Exception {
        boolean verifyTrue = SecurityUtil.verifyRSA(SecurityUtil.parsePublicKey(new Base64().decode(RandomOrgServiceHandler.RANDOM_ORG_PUB_KEY)),
                SecurityUtil.sha512("{\"method\":\"generateSignedBlobs\",\"hashedApiKey\":\"c79p3+jyxhJPzAFSqsWQfmQnPaSazodEPIYD1gktjajfCfKeKN7O4miE2F/8rTc/ol3AowJUqaB296oHXEcudg==\",\"n\":1,\"size\":192,\"format\":\"base64\",\"data\":[\"fR4w+uQBtriQhrjTJF5saw8gW18Oh7wA\"],\"completionTime\":\"2017-08-19 12:51:05Z\",\"serialNumber\":213}"),
                "Sw5GhRmUjDLjKWvQP4OowbA02niahXtA9bkG5WkUtx47O4lKrXLo0OCna7nLH/cPfl6UX/KwnnVMByxPtkTZxleaP6DZbJeWCyh/xRUBFjCX6FTgEF3ixE5FRm95CzXyq/wN6cd7sSpt0hlLvnahQgB76WMI/Wyhs2C6z6IZ0EbkdExlsl47dBCFo+N0WmPSjReQEN0UZGxp/QlsCbSAtWC2o9MbBKMydeXdQoFwO9CZ4Nh6CfaNxcHhHOM8kVYGObpLPZMsdSeXjFB+2xJd4TrokJOVTTcbSTv7HBqD3sWuRuEiDUwWV65D6QK+hYZjZgIYO/pAeou/ygvv4+526mBk7aKiwFaIjmIFLvx6tDSvMeJq/Lo+oiILd0u41jDs2LlxxxVOuRX1MgHYJWYi8O4snoIHUx9OULsn5NOtZdnrzN/YaUVDDia2WtbIGoJNeoFVo3Z1oIXzTnYDheTn/o5sRPgPneXJuFcbss8uFguVG52MO9kCLHWWwhVlA9mIckwhG26swlLVmbNbsDbBVdkyu3Um5+fDtcwBvttUf0CJahToY4gjNZeHA/85B/5E8/PUtFDUiCqCkmkWlQWGU+Pv4hWZOO21gUCqgog0zrRcQrXQpQ+w/NxCrwFbRNDYEfe4tlF99kTsPe0oAQ3pP1CGvspyEWN7yREvGHP9m9o=");

        assertTrue(verifyTrue);
    }

    @Test
    public void parsePublicKeyAndTestEncrypt() throws Exception {
        PublicKey publicKey = SecurityUtil.parsePublicKey(new Base64().decode(RandomOrgServiceHandler.RANDOM_ORG_PUB_KEY));

        byte[] plain = new byte[]{0, 1, 4, 2, 1, 2, 3, 4, 2};
        Cipher c = Cipher.getInstance("RSA");
        c.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encrypted = c.doFinal(new byte[]{0, 1, 4, 2, 1, 2, 3, 4, 2});
        assertFalse(Arrays.equals(plain, encrypted));

    }

}