package vn.iotstar.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public final class FormUtil {
    private FormUtil() {}

    /** Lấy parameter, đã trim, không bao giờ null. */
    public static String param(HttpServletRequest req, String name) {
        String v = req.getParameter(name);
        return v == null ? "" : v.trim();
    }

    public static int parseInt(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return def;
        }
    }

    /** Lấy file upload; nếu vượt giới hạn dung lượng của @MultipartConfig thì ghi lỗi vào errors. */
    public static Part part(HttpServletRequest req, String name, Map<String, String> errors) throws IOException, ServletException {
        try {
            return req.getPart(name);
        } catch (IllegalStateException e) {
            errors.put(name, "Ảnh vượt quá " + Constants.MAX_IMAGE_MB + "MB");
            return null;
        }
    }

    /** Lưu ảnh vào thư mục upload, trả về tên file mới (hoặc null nếu không có file). */
    public static String saveImage(Part part) throws IOException {
        if (part == null || part.getSize() <= 0) return null;
        File dir = new File(Constants.UPLOAD_DIR);
        if (!dir.exists()) dir.mkdirs();
        String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
        String ext = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        String fname = System.currentTimeMillis() + "." + ext;
        part.write(new File(dir, fname).getAbsolutePath());
        return fname;
    }

    /** Xóa file ảnh cũ trong thư mục upload (bỏ qua link http và ảnh mặc định). */
    public static void deleteOldImage(String fname) {
        if (fname == null || fname.isBlank() || fname.startsWith("http") || fname.equals("avatar.png")) return;
        try {
            File f = new File(Constants.UPLOAD_DIR, Paths.get(fname).getFileName().toString());
            if (f.isFile()) Files.delete(f.toPath());
        } catch (Exception ignored) {
        }
    }
}
