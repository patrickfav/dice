package at.favre.tools.dice;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class RndToolRandomHandler {
    /**
     * Create a new SecureRandom from a strong source. Will immediatly create small number
     * of random bytes to fixate the OS seed.
     *
     * @return new secure random instance
     * @throws NoSuchAlgorithmException
     */
    public static SecureRandom createSecureRandom() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstanceStrong();

        /*
         * Right after the SecureRandom constructor, perform a single nextBytes with some small numBytes > 0
         * and disregard the result (even though 0 should do if a Vulcan implemented the spec), then setSeed()
         * with whatever unpredictable data at hand, then use nextBytes.
         *
         * https://crypto.stackexchange.com/a/51222/44838
         */
        secureRandom.nextBytes(new byte[4]);

        return secureRandom;
    }

    /**
     * Second hand seeds given secureRandom (That means a new Secure Random receives the external seeds, then
     * creates a seed for given one
     *
     * @param secureRandom wil be seeded
     * @param externalSeed the used seed (second hand)
     * @throws NoSuchAlgorithmException
     */
    public static void seed(SecureRandom secureRandom, byte[] externalSeed) throws NoSuchAlgorithmException {
        SecureRandom tempSecureRandom = createSecureRandom();
        tempSecureRandom.setSeed(externalSeed);
        secureRandom.setSeed(tempSecureRandom.generateSeed(16));
    }
}
