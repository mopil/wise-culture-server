package mjucapstone.wiseculture.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 비밀번호 암호화
 */
public class EncryptManager {

    public static String hash(String str) {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            byte[] byteData = md.digest();
            StringBuffer hexString = new StringBuffer();
            for (byte b : byteData) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static boolean check(String password, String hashed) {
        String encryptedPassword = EncryptManager.hash(password);
        return hashed.equals(encryptedPassword);
    }

}
