package edu.vassar.cmpu203.workoutapp.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class AuthKey implements Serializable {
    private String salt;
    private String key;

    public AuthKey(){}

    public AuthKey(@NonNull String password){
        this(AuthKey.generateSalt(), password);
    }

    public AuthKey(@NonNull String salt, @NonNull String password) {
        this.salt = salt;
        this.key = this.generateKey(salt, password);
    }

    public String getSalt(){
        return this.salt;
    }

    public String getKey(){
        return this.key;
    }

    public boolean validatePassword(String password){
        AuthKey oauthKey = new AuthKey(this.salt, password);
        return this.key.equals(oauthKey.key);
    }

    @Override
    @NonNull
    public String toString() {
        return String.format("salt: %s, key: %s", this.salt, this.key);
    }

    private static final int SALT_LEN = 20;
    private static final int KEY_LEN = 40;
    private static final int NITERS = 64000;

    @NonNull
    private static String generateSalt() {
        byte[] salt = new byte[SALT_LEN];
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            Log.e("NextGenPos", "Error generating authentication salt", e);
        }
        String saltStr = Base64.getEncoder().encodeToString(salt);
        return saltStr;
    }


    private static String generateKey(String salt, String password) {
        String hashStr = null;
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            byte[] saltBytes = Base64.getDecoder().decode(salt);
            char[] chars = password.toCharArray();
            PBEKeySpec spec = new PBEKeySpec(chars, saltBytes, NITERS, KEY_LEN * Byte.SIZE);

            byte[] hashBytes = skf.generateSecret(spec).getEncoded();
            hashStr = Base64.getEncoder().encodeToString(hashBytes);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            Log.e("NextGenPos", "Error generating authentication key", e);
        }
        return hashStr;
    }


}
