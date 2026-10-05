package vn.iotstar.util;

import jakarta.servlet.http.Part;

import java.nio.file.Paths;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Các hàm validate phía server. Mỗi hàm trả về thông báo lỗi (String)
 * hoặc null nếu hợp lệ.
 */
public final class ValidationUtil {
    private ValidationUtil() {}

    private static final Pattern USERNAME = Pattern.compile("^[A-Za-z0-9_]{4,30}$");
    private static final Pattern EMAIL = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");
    private static final Pattern PHONE = Pattern.compile("^(0|\\+84)[0-9]{9}$");
    private static final Pattern LINK = Pattern.compile("^https?://\\S+$", Pattern.CASE_INSENSITIVE);
    public static final Set<String> IMAGE_EXT = Set.of("jpg", "jpeg", "png", "gif", "webp");

    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    public static String username(String v) {
        if (isBlank(v)) return "Vui lòng nhập tên đăng nhập";
        if (!USERNAME.matcher(v).matches()) return "Tên đăng nhập 4-30 ký tự, chỉ gồm chữ, số và dấu _";
        return null;
    }

    public static String password(String v) {
        if (isBlank(v)) return "Vui lòng nhập mật khẩu";
        if (v.length() < 6) return "Mật khẩu tối thiểu 6 ký tự";
        if (v.length() > 50) return "Mật khẩu tối đa 50 ký tự";
        return null;
    }

    public static String email(String v) {
        if (isBlank(v)) return "Vui lòng nhập email";
        if (v.length() > 100 || !EMAIL.matcher(v).matches()) return "Email không hợp lệ";
        return null;
    }

    public static String fullname(String v) {
        if (isBlank(v)) return "Vui lòng nhập họ tên";
        if (v.trim().length() < 2 || v.trim().length() > 100) return "Họ tên từ 2 đến 100 ký tự";
        return null;
    }

    public static String phone(String v) {
        if (isBlank(v)) return "Vui lòng nhập số điện thoại";
        if (!PHONE.matcher(v).matches()) return "Số điện thoại không hợp lệ (VD: 0912345678 hoặc +84912345678)";
        return null;
    }

    public static String categoryName(String v) {
        if (isBlank(v)) return "Vui lòng nhập tên category";
        if (v.trim().length() < 2 || v.trim().length() > 100) return "Tên category từ 2 đến 100 ký tự";
        return null;
    }

    public static String status(String v) {
        if ("0".equals(v) || "1".equals(v)) return null;
        return "Trạng thái không hợp lệ";
    }

    /** Link ảnh: không bắt buộc, nhưng nếu nhập thì phải là http/https. */
    public static String imageLink(String v) {
        if (isBlank(v)) return null;
        if (v.length() > 255 || !LINK.matcher(v.trim()).matches()) return "Link ảnh phải bắt đầu bằng http:// hoặc https://";
        return null;
    }

    /** File ảnh upload: không bắt buộc, nhưng nếu chọn thì phải đúng định dạng và dung lượng. */
    public static String imagePart(Part part) {
        if (part == null || part.getSize() <= 0) return null;
        String name = part.getSubmittedFileName();
        if (name == null || !name.contains(".")) return "File ảnh không hợp lệ";
        String ext = Paths.get(name).getFileName().toString();
        ext = ext.substring(ext.lastIndexOf('.') + 1).toLowerCase();
        if (!IMAGE_EXT.contains(ext)) return "Chỉ chấp nhận ảnh jpg, jpeg, png, gif, webp";
        if (part.getContentType() == null || !part.getContentType().startsWith("image/")) return "File tải lên phải là ảnh";
        if (part.getSize() > Constants.MAX_IMAGE_MB * 1024L * 1024L) return "Ảnh vượt quá " + Constants.MAX_IMAGE_MB + "MB";
        return null;
    }
}
