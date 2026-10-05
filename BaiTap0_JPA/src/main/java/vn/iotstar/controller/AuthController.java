package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.entity.User;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.UserServiceImpl;
import vn.iotstar.util.Constants;
import vn.iotstar.util.FormUtil;
import vn.iotstar.util.ValidationUtil;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/login", "/logout", "/register"})
public class AuthController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final IUserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();
        if ("/logout".equals(path)) {
            HttpSession session = req.getSession(false);
            if (session != null) session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
        } else if ("/register".equals(path)) {
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
        } else {
            if (req.getSession(false) != null && req.getSession(false).getAttribute(Constants.SESSION_ACCOUNT) != null) {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }
            req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if ("/register".equals(req.getServletPath())) {
            register(req, resp);
        } else {
            login(req, resp);
        }
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errors = new LinkedHashMap<>();
        Map<String, String> form = new LinkedHashMap<>();
        String username = FormUtil.param(req, "username");
        String password = req.getParameter("password");
        form.put("username", username);

        if (ValidationUtil.isBlank(username)) errors.put("username", "Vui lòng nhập tên đăng nhập");
        if (ValidationUtil.isBlank(password)) errors.put("password", "Vui lòng nhập mật khẩu");

        if (errors.isEmpty()) {
            User user = userService.login(username, password);
            if (user == null) {
                errors.put("global", "Sai tên đăng nhập hoặc mật khẩu");
            } else {
                req.getSession().invalidate();          // chống session fixation
                HttpSession session = req.getSession(true);
                session.setAttribute(Constants.SESSION_ACCOUNT, user);
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }
        }
        req.setAttribute("errors", errors);
        req.setAttribute("form", form);
        req.getRequestDispatcher("/views/web/login.jsp").forward(req, resp);
    }

    private void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> errors = new LinkedHashMap<>();
        Map<String, String> form = new LinkedHashMap<>();
        String username = FormUtil.param(req, "username");
        String email = FormUtil.param(req, "email");
        String fullname = FormUtil.param(req, "fullname");
        String phone = FormUtil.param(req, "phone");
        String password = req.getParameter("password");
        String confirm = req.getParameter("confirm");
        form.put("username", username);
        form.put("email", email);
        form.put("fullname", fullname);
        form.put("phone", phone);

        String err;
        if ((err = ValidationUtil.username(username)) != null) errors.put("username", err);
        else if (userService.findByUsername(username) != null) errors.put("username", "Tên đăng nhập đã tồn tại");

        if ((err = ValidationUtil.email(email)) != null) errors.put("email", err);
        else if (userService.findByEmail(email) != null) errors.put("email", "Email đã được sử dụng");

        if ((err = ValidationUtil.fullname(fullname)) != null) errors.put("fullname", err);
        if ((err = ValidationUtil.phone(phone)) != null) errors.put("phone", err);
        if ((err = ValidationUtil.password(password)) != null) errors.put("password", err);
        else if (!password.equals(confirm)) errors.put("confirm", "Mật khẩu nhập lại không khớp");

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("form", form);
            req.getRequestDispatcher("/views/web/register.jsp").forward(req, resp);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setFullname(fullname);
        user.setPhone(phone);
        userService.register(user, password);
        resp.sendRedirect(req.getContextPath() + "/login?registered=1");
    }
}
