package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.UserServiceImpl;
import vn.iotstar.util.Constants;
import vn.iotstar.util.FormUtil;
import vn.iotstar.util.ValidationUtil;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/** Profile của User: xem và cập nhật fullname, phone, images (upload multipart). */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
@WebServlet(urlPatterns = {"/profile", "/profile/update"})
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = currentUser(req);
        Map<String, String> form = new LinkedHashMap<>();
        form.put("fullname", nvl(user.getFullname()));
        form.put("phone", nvl(user.getPhone()));
        req.setAttribute("user", user);
        req.setAttribute("form", form);
        req.getRequestDispatcher("/views/web/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User user = currentUser(req);
        Map<String, String> errors = new LinkedHashMap<>();
        Map<String, String> form = new LinkedHashMap<>();

        Part part = FormUtil.part(req, "images1", errors);   // phải gọi trước khi đọc các field khác
        boolean uploadTooBig = errors.containsKey("images1");   // request multipart bị từ chối -> không đọc được field khác
        String fullname = uploadTooBig ? nvl(user.getFullname()) : FormUtil.param(req, "fullname");
        String phone = uploadTooBig ? nvl(user.getPhone()) : FormUtil.param(req, "phone");
        form.put("fullname", fullname);
        form.put("phone", phone);

        String err;
        if (!uploadTooBig) {
            if ((err = ValidationUtil.fullname(fullname)) != null) errors.put("fullname", err);
            if ((err = ValidationUtil.phone(phone)) != null) errors.put("phone", err);
        }
        if (!errors.containsKey("images1") && (err = ValidationUtil.imagePart(part)) != null) errors.put("images1", err);

        if (!errors.isEmpty()) {
            req.setAttribute("user", user);
            req.setAttribute("errors", errors);
            req.setAttribute("form", form);
            req.getRequestDispatcher("/views/web/profile.jsp").forward(req, resp);
            return;
        }

        user.setFullname(fullname);
        user.setPhone(phone);
        String fname = FormUtil.saveImage(part);
        String oldImage = user.getImages();
        if (fname != null) user.setImages(fname);
        userService.update(user);
        if (fname != null) FormUtil.deleteOldImage(oldImage);

        req.getSession().setAttribute(Constants.SESSION_ACCOUNT, user);   // cập nhật lại navbar
        resp.sendRedirect(req.getContextPath() + "/profile?success=1");
    }

    /** Luôn đọc lại user từ DB theo id trong session để dữ liệu mới nhất. */
    private User currentUser(HttpServletRequest req) {
        User sessionUser = (User) req.getSession().getAttribute(Constants.SESSION_ACCOUNT);
        User fresh = userService.findById(sessionUser.getId());
        return fresh != null ? fresh : sessionUser;
    }

    private String nvl(String s) {
        return s == null ? "" : s;
    }
}
