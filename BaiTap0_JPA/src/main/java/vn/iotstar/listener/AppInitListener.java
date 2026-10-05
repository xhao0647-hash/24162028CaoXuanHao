package vn.iotstar.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.iotstar.service.IUserService;
import vn.iotstar.service.UserServiceImpl;

/** Khi khởi động: khởi tạo JPA (tự tạo bảng) và tạo tài khoản admin/123456 nếu chưa có user nào. */
@WebListener
public class AppInitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            IUserService userService = new UserServiceImpl();
            userService.seedDefaultUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
