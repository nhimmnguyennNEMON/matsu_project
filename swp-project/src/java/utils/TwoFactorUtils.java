package utils;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.Timestamp;
import java.util.Date;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base32;

public class TwoFactorUtils {

    public static String generateSecret(int length) {
        StringBuilder sb = new StringBuilder(length);
        Random random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int val = random.nextInt(32);
            if (val < 26) {
                sb.append((char) ('A' + val));
            } else {
                sb.append((char) ('2' + (val - 26)));
            }
        }
        return sb.toString();
    }

    public static long generateNumber(String base32Secret, long timeMillis, long timeStepSeconds)
            throws GeneralSecurityException {
        byte[] key = new Base32().decode(base32Secret);
        byte[] data = new byte[8];
        long value = timeMillis / 1000 / timeStepSeconds;
        for (int i = 7; value > 0; i--) {
            data[i] = (byte) (value & 0xFF);
            value >>= 8;
        }
        SecretKeySpec signKey = new SecretKeySpec(key, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(signKey);
        byte[] hash = mac.doFinal(data);
        int offset = hash[hash.length - 1] & 0xF;
        long truncatedHash = 0;
        for (int i = offset; i < offset + 4; ++i) {
            truncatedHash <<= 8;
            // get the 4 bytes at the offset
            truncatedHash |= (hash[i] & 0xFF);
        }
        truncatedHash &= 0x7FFFFFFF;
        truncatedHash %= 1000000;

        return truncatedHash;
    }

    public static long getSecretKey(String base32Secret) throws GeneralSecurityException {
        Long code = null;
        long DEFAULT_TIME_STEP_SECONDS = 30;
        long diff = DEFAULT_TIME_STEP_SECONDS - ((System.currentTimeMillis() / 1000) % DEFAULT_TIME_STEP_SECONDS);
        code = generateNumber(base32Secret, System.currentTimeMillis(), DEFAULT_TIME_STEP_SECONDS);
        return code;
    }

    public static void main(String[] args) throws Exception {

        String base_secret = "VEXUDL4AECLTWTUYHGU2GK3B5LWPCKDU";
       System.out.println(generateSecret(32));
        //System.out.println(getSecretKey(base_secret));
    }

}
//https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=200x200&chld=M%7C0&cht=qr&chl=otpauth://totp/MATSU:hungvt164@gmail.com%3Fsecret%VEXUDL4AECLTWTUYHGU2GK3B5LWPCKDU%26issuer%3DMATSU
