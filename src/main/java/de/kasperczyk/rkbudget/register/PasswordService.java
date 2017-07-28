package de.kasperczyk.rkbudget.register;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

@Service
public class PasswordService {

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    // https://stackoverflow.com/questions/18142745/how-do-i-generate-a-salt-in-java-for-salted-hash
    String saltAndHash(String password) {
        return new String(saltAndHash(password.toCharArray()));
    }

    private byte[] saltAndHash(char[] password) {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password, nextSalt(), ITERATIONS, KEY_LENGTH);
        Arrays.fill(password, Character.MIN_VALUE);
        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new AssertionError("Error while hashing a password: " + e.getMessage(), e);
        } finally {
            pbeKeySpec.clearPassword();
        }
    }

    private byte[] nextSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return salt;
    }
}
