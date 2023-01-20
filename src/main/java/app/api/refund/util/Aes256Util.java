package app.api.refund.util;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Aes256Util {

    private final String ALGORITHM;
    private final String AES_SECRET_KEY;
    SecureRandom random = new SecureRandom();


    public Aes256Util(@Value("${aes.algorithm}") String algorithm,
            @Value("${aes.secret-key}") String aes_secret_key) {
        ALGORITHM = algorithm;
        AES_SECRET_KEY = aes_secret_key;
    }

    public String encryptAES256(String text, String iv)  {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(AES_SECRET_KEY.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

            byte[] encrypted = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String decryptAES256(String cipherText, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec keySpec = new SecretKeySpec(AES_SECRET_KEY.getBytes(), "AES");
            IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);

            byte[] decodedBytes = Base64.getDecoder().decode(cipherText);
            byte[] decrypted = cipher.doFinal(decodedBytes);
            return new String(decrypted, StandardCharsets.UTF_8);
        }catch (Exception e){
            throw new RuntimeException(e);

        }

    }

    public String generateIv(){

        return RandomStringUtils.randomAlphanumeric(16);

    }
}
