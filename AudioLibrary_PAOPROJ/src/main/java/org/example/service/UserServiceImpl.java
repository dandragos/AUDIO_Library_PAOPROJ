package org.example.service;

import org.example.dao.UserDao;
import org.example.dao.UserDaoImpl;
import org.example.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private boolean loggedIn;
    private User currentUser;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
        this.loggedIn = false;
        this.currentUser = null;
    }

    @Override
    public boolean register(String username, String password) {
        if (userDao.login(username, password) != null) {
            return false;
        }

        User user = new User(username, password, "USER");
        userDao.register(user);
        return true;
    }

    @Override
    public User login(String username, String password) {
        if (loggedIn) {
            System.out.println("You are already logged in.");
            return null;
        }
        User user = userDao.login(username, password);
        if (user != null) {
            loggedIn = true;
            currentUser = user;
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
        return user;
    }

    @Override
    public void logout() {
        if (!loggedIn) {
            System.out.println("You are not logged in.");
            return;
        }
        loggedIn = false;
        currentUser = null;
        System.out.println("Logout successful!");
    }

    @Override
    public void promoteUser(String username) {
        if (!loggedIn) {
            System.out.println("You must be logged in to perform this action.");
            return;
        }

        if (!"ADMIN".equals(currentUser.getRole())) {
            System.out.println("Only admins can promote users.");
            return;
        }

        userDao.promoteToAdmin(username);
        System.out.println("User promoted to ADMIN role.");
    }

    @Override
    public void demoteUser(String username) {
        if (!loggedIn) {
            System.out.println("You must be logged in to perform this action.");
            return;
        }

        if (!"ADMIN".equals(currentUser.getRole())) {
            System.out.println("Only admins can demote users.");
            return;
        }

        userDao.demoteToUser(username);
        System.out.println("User demoted to USER role.");
    }

    @Override
    public void showAllUsers() {
        if (!loggedIn) {
            System.out.println("You must be logged in to perform this action.");
            return;
        }
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user.getUsername() + " - " + user.getRole());
        }
    }

    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }



    // Metodă pentru a obține utilizatorul curent autentificat
    public User getCurrentLoggedInUser() {
        return currentUser;
    }
}
