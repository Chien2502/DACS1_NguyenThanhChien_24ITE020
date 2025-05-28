package controller;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    /**
     * Tạo hash của mật khẩu raw
     * @param rawPassword mật khẩu thuần (plain-text)
     * @param cost work factor (mặc định 10–12). Số càng lớn càng chậm.
     * @return chuỗi hash đã bao gồm salt và cost
     */
    public static String hashPassword(String rawPassword, int cost) {
        // gensalt(cost) sẽ sinh salt + lưu cost vào kết quả
        String salt = BCrypt.gensalt(cost);
        return BCrypt.hashpw(rawPassword, salt);
    }

    /**
     * Kiểm tra mật khẩu người dùng nhập so với hash lưu trong DB
     * @param rawPassword mật khẩu nhập
     * @param storedHash hash lấy từ DB
     * @return true nếu khớp, false nếu sai
     */
    public static boolean verifyPassword(String rawPassword, String storedHash) {
        if (storedHash == null || !storedHash.startsWith("$2a$")) {
            throw new IllegalArgumentException("Invalid hash provided for comparison");
        }
        return BCrypt.checkpw(rawPassword, storedHash);
    }
//    public static void main(String[] args) {
//    	String plain = "123";
//    	String hash  = PasswordUtils.hashPassword(plain, 12);
//    	System.out.println(hash);
//	}
}
