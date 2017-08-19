package at.favre.tools.dice.util;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class SecurityUtil {


    public static byte[] sha512(String raw) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            return md.digest(raw.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("could not hash", e);
        }
    }

    public static String base64EncodedSha512(String raw) {
        return new Base64().encodeToString(sha512(raw));
    }

    public static boolean verifyRSA(PublicKey publicKey, byte[] plain, String signatureBase64) {
        try {
            Signature sig = Signature.getInstance("SHA512withRSA");
            sig.initVerify(publicKey);
            sig.update(plain);
            return sig.verify(new Base64().decode(signatureBase64));
        } catch (Exception e) {
            throw new IllegalStateException("could not verify", e);
        }
    }

    public static PublicKey parsePublicKey(byte[] keyBytes) throws Exception {
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        X509Certificate cer = (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(keyBytes));
        return cer.getPublicKey();
    }

}
