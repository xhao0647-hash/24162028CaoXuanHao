package vn.iotstar.util;

public final class Constants {
    private Constants() {}

    /** Thư mục lưu ảnh upload. Có thể đổi bằng -Dupload.dir=... */
    public static final String UPLOAD_DIR = System.getProperty("upload.dir", "D:\\uploads");

    /** Tên attribute lưu user đã đăng nhập trong session. */
    public static final String SESSION_ACCOUNT = "account";

    public static final int MAX_IMAGE_MB = 5;
}
