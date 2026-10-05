package vn.iotstar.service;

import vn.iotstar.entity.User;

public interface IUserService {
    /** Trả về user nếu đúng username/password, ngược lại null. */
    User login(String username, String password);
    void register(User user, String rawPassword);
    void update(User user);
    User findById(int id);
    User findByUsername(String username);
    User findByEmail(String email);
    /** Tạo tài khoản admin/123456 nếu bảng users đang trống. */
    void seedDefaultUser();
}
