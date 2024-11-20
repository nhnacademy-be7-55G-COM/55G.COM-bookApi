package shop.s5g.bookApi.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HashUtil {
    public static String toMD5Hash(String input) {
        try {
            // 1. MessageDigest 인스턴스 생성
            MessageDigest md = MessageDigest.getInstance("MD5");

            // 2. 입력 값을 MD5로 해싱
            byte[] hashBytes = md.digest(input.getBytes());

            // 3. 해시된 바이트 배열을 16진수 문자열로 변환
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b); // 각 바이트를 16진수로 변환
                if (hex.length() == 1) {
                    hexString.append('0'); // 한 자리일 경우 0 추가
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not found", e);
        }
    }
}