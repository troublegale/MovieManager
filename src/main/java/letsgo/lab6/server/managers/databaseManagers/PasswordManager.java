package letsgo.lab6.server.managers.databaseManagers;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordManager {

    public static String getSalt() {
        return Integer.toHexString(new Random().nextInt());
    }

    public static String getHash(String password, String salt) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = md.digest((salt + password).getBytes(StandardCharsets.UTF_8));
        BigInteger bigInt = new BigInteger(1, hash);
        StringBuilder strHash = new StringBuilder(bigInt.toString(16));
        while (strHash.length() < 96) {
            strHash.insert(0, "0");
        }
        return strHash.toString();
    }

}
