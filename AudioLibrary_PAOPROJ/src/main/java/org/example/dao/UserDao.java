package org.example.dao;


import org.example.model.User;

import java.util.List;

public interface UserDao {
    void register(User user);
    User login(String username, String password);
    void promoteToAdmin(String username);
    void demoteToUser(String username);
    List<User> getAllUsers();
    User getUserByUsername(String username);
}
