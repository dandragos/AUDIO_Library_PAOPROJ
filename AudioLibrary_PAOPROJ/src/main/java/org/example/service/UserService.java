package org.example.service;


import org.example.model.User;

public interface UserService {
    boolean register(String username, String password);
    User login(String username, String password);
    void logout();
    void promoteUser(String username);
    void demoteUser(String username);
    void showAllUsers();

    boolean isLoggedIn();
}
