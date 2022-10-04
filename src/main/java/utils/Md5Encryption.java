package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;

public class Md5Encryption {
    private static final char[] CHARACTER_DEFAULT =
                    {'a', 'b', 'c', 'd', 'e', 'f',
                    'g', 'h', 'i', 'j', 'k', 'l', 'm',
                    'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', '1', '2',
                    '3', '4', '5', '6', '7', '8', '9', '0'};
    public static String getRandomSalt(int length){
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<length;i++){
            int index = random.nextInt(CHARACTER_DEFAULT.length);
            stringBuffer.append(CHARACTER_DEFAULT[index]);
        }
        return stringBuffer.toString();
    }
    public static String getEncryption(String password,String salt) throws NoSuchAlgorithmException {
        int pwdIndex = password.length() / 2;
        int saltIndex = salt.length() / 2;
        String encodeString = password.substring(0,pwdIndex)+salt.substring(0,saltIndex)
                +password.substring(pwdIndex)+salt.substring(saltIndex);
        MessageDigest md5 = MessageDigest.getInstance("Md5");
        byte[] digest = md5.digest(encodeString.getBytes());
        return Base64.getEncoder().encodeToString(digest);
    }
}
