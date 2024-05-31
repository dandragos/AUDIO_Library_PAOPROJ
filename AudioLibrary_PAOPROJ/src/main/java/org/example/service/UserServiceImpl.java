package org.example.service;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public boolean register(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }
        User user = new User(username, password, "USER");
        userDao.register(user);
        return true;
    }

    @Override
    public User login(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return null;
        }
        return userDao.login(username, password);
    }

    @Override
    public void logout() {
        // Implementare logout dacă e necesar, în funcție de context
    }

    @Override
    public void promoteUser(String username) {
        userDao.promoteToAdmin(username);
    }

    @Override
    public void demoteUser(String username) {
        userDao.demoteToUser(username);
    }

    @Override
    public List<User> showAllUsers() {
        List<User> users = userDao.getAllUsers();
        users.forEach(user -> System.out.println(user.getUsername() + " - " + user.getRole()));
        return users;
    }
}
