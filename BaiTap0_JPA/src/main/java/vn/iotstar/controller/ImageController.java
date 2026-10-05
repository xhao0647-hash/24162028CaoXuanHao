package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.util.Constants;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/** Trả ảnh trong thư mục upload: /image?fname=xxx.png (nếu không có ảnh thì trả ảnh placeholder). */
@WebServlet(urlPatterns = {"/image"})
public class ImageController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String PLACEHOLDER =
            "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"200\" height=\"150\" viewBox=\"0 0 200 150\">"
          + "<rect width=\"200\" height=\"150\" fill=\"#e9ecef\"/>"
          + "<text x=\"100\" y=\"80\" font-size=\"16\" text-anchor=\"middle\" fill=\"#6c757d\" "
          + "font-family=\"Arial\">No image</text></svg>";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fname = req.getParameter("fname");
        File file = null;
        if (fname != null && !fname.isBlank()) {
            try {
                // getFileName() chặn path traversal kiểu ../../
                file = new File(Constants.UPLOAD_DIR, Paths.get(fname).getFileName().toString());
            } catch (Exception e) {
                file = null;
            }
        }
        String mime = file == null ? null : getServletContext().getMimeType(file.getName());
        if (file == null || !file.isFile() || mime == null || !mime.startsWith("image/")) {
            resp.setContentType("image/svg+xml");
            resp.getOutputStream().write(PLACEHOLDER.getBytes(StandardCharsets.UTF_8));
            return;
        }
        resp.setContentType(mime);
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
