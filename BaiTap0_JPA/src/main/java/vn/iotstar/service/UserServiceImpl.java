package vn.iotstar.service;

import vn.iotstar.dao.IUserDao;
import vn.iotstar.dao.UserDaoImpl;
import vn.iotstar.entity.User;
import vn.iotstar.util.PasswordUtil;

public class UserServiceImpl implements IUserService {
    private final IUserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User u = userDao.findByUsername(username);
        if (u != null && u.getPassword().equals(PasswordUtil.hash(password))) return u;
        return null;
    }

    @Override
    public void register(User user, String rawPassword) {
        user.setPassword(PasswordUtil.hash(rawPassword));
        userDao.insert(user);
    }

    @Override
    public void update(User user) { userDao.update(user); }

    @Override
    public User findById(int id) { return userDao.findById(id); }

    @Override
    public User findByUsername(String username) { return userDao.findByUsername(username); }

    @Override
    public User findByEmail(String email) { return userDao.findByEmail(email); }

    @Override
    public void seedDefaultUser() {
        if (userDao.count() == 0) {
            User u = new User();
            u.setUsername("admin");
            u.setEmail("admin@iotstar.vn");
            u.setFullname("Quản trị viên");
            u.setPhone("0912345678");
            register(u, "123456");
        }
    }
}
