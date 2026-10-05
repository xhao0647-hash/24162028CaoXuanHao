# BaiTap02_JPA

JPA (Hibernate 7) + SQL Server + Servlet/JSP + **Sitemesh 3** (Bootstrap 5).

## Chạy
1. SQL Server: tạo database `webst2`, chỉnh user/password trong `src/main/resources/META-INF/persistence.xml`.
2. Tomcat 10.1+ (Jakarta), deploy war `BaiTap02_JPA`.
3. Ảnh upload lưu tại `D:\uploads` (đổi bằng `-Dupload.dir=...`).
4. Lần chạy đầu tự tạo bảng và tài khoản **admin / 123456**.

## Yêu cầu bài tập
1. **Sitemesh 3 + 01 template Bootstrap** tích hợp bài 03: dependency `org.sitemesh:sitemesh:3.2.0` trong `pom.xml`,
   cấu hình ở `filter/SiteMeshFilter.java`, decorator duy nhất `WEB-INF/decorators/main.jsp`.
   Mọi trang (login, register, home, profile, category) chỉ chứa phần nội dung, Sitemesh tự bọc layout.
2. **Validation cho mọi form** (client: HTML5 + Bootstrap `was-validated`; server: `util/ValidationUtil.java`):
   login, register, category add/edit, profile. Lỗi server hiển thị lại ngay dưới từng ô và giữ nguyên dữ liệu đã nhập.
3. **Profile** `/profile`: cập nhật fullname, phone, images (upload multipart, tối đa 5MB, chỉ nhận jpg/png/gif/webp).
