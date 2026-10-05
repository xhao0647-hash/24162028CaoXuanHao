package vn.iotstar.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.entity.Category;
import vn.iotstar.service.CategoryServiceImpl;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.util.FormUtil;
import vn.iotstar.util.ValidationUtil;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** CRUD Category (có validation client + server, upload ảnh multipart). */
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 10)
@WebServlet(urlPatterns = {"/admin/categories", "/admin/category/add", "/admin/category/insert",
        "/admin/category/edit", "/admin/category/update", "/admin/category/delete"})
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final ICategoryService cateService = new CategoryServiceImpl();

    private static final String LIST_VIEW = "/views/admin/category-list.jsp";
    private static final String ADD_VIEW = "/views/admin/category-add.jsp";
    private static final String EDIT_VIEW = "/views/admin/category-edit.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();
        switch (path) {
            case "/admin/category/add" -> {
                Map<String, String> form = new LinkedHashMap<>();
                form.put("status", "1");
                req.setAttribute("form", form);
                req.getRequestDispatcher(ADD_VIEW).forward(req, resp);
            }
            case "/admin/category/edit" -> {
                Category c = cateService.findById(FormUtil.parseInt(req.getParameter("id"), -1));
                if (c == null) {
                    resp.sendRedirect(req.getContextPath() + "/admin/categories");
                    return;
                }
                req.setAttribute("cate", c);
                req.setAttribute("form", toForm(c));
                req.getRequestDispatcher(EDIT_VIEW).forward(req, resp);
            }
            case "/admin/category/delete" -> {
                int id = FormUtil.parseInt(req.getParameter("id"), -1);
                Category c = cateService.findById(id);
                try {
                    cateService.delete(id);
                    if (c != null) FormUtil.deleteOldImage(c.getImages());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
            }
            default -> {
                List<Category> list = cateService.findAll();
                req.setAttribute("listcate", list);
                req.getRequestDispatcher(LIST_VIEW).forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if ("/admin/category/insert".equals(req.getServletPath())) {
            save(req, resp, null);
        } else if ("/admin/category/update".equals(req.getServletPath())) {
            Category c = cateService.findById(FormUtil.parseInt(req.getParameter("categoryid"), -1));
            if (c == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
                return;
            }
            save(req, resp, c);
        }
    }

    /** Dùng chung cho insert (existing == null) và update. */
    private void save(HttpServletRequest req, HttpServletResponse resp, Category existing)
            throws ServletException, IOException {
        boolean isEdit = existing != null;
        Map<String, String> errors = new LinkedHashMap<>();
        Map<String, String> form = new LinkedHashMap<>();

        Part part = FormUtil.part(req, "images1", errors);   // gọi trước khi đọc các field khác
        String name = FormUtil.param(req, "categoryname");
        String status = FormUtil.param(req, "status");
        String link = FormUtil.param(req, "images");
        form.put("categoryname", name);
        form.put("status", status);
        form.put("images", link);

        String err;
        if (!errors.containsKey("images1")) {   // ảnh quá lớn -> request bị từ chối, các field khác rỗng nên bỏ qua
            if ((err = ValidationUtil.categoryName(name)) != null) errors.put("categoryname", err);
            if ((err = ValidationUtil.status(status)) != null) errors.put("status", err);
            if ((err = ValidationUtil.imageLink(link)) != null) errors.put("images", err);
        }
        if (!errors.containsKey("images1") && (err = ValidationUtil.imagePart(part)) != null) errors.put("images1", err);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("form", form);
            if (isEdit) req.setAttribute("cate", existing);
            req.getRequestDispatcher(isEdit ? EDIT_VIEW : ADD_VIEW).forward(req, resp);
            return;
        }

        Category c = isEdit ? existing : new Category();
        String oldImage = c.getImages();
        c.setCategoryname(name);
        c.setStatus(Integer.parseInt(status));

        String fname = FormUtil.saveImage(part);          // ưu tiên file upload
        if (fname != null) {
            c.setImages(fname);
        } else if (!link.isEmpty()) {
            c.setImages(link);
        } else if (!isEdit) {
            c.setImages("avatar.png");
        }

        if (isEdit) cateService.update(c); else cateService.insert(c);
        if (isEdit && c.getImages() != null && !c.getImages().equals(oldImage)) FormUtil.deleteOldImage(oldImage);
        resp.sendRedirect(req.getContextPath() + "/admin/categories");
    }

    private Map<String, String> toForm(Category c) {
        Map<String, String> form = new LinkedHashMap<>();
        form.put("categoryname", c.getCategoryname() == null ? "" : c.getCategoryname());
        form.put("status", String.valueOf(c.getStatus()));
        form.put("images", c.getImages() != null && c.getImages().startsWith("http") ? c.getImages() : "");
        return form;
    }
}
