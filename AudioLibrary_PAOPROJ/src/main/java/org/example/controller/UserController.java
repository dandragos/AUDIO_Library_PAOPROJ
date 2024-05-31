package org.example.controller;

import lombok.Getter;
import org.example.model.User;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public class UserController {
    private final UserService userService = new UserServiceImpl();
    private final Scanner scanner = new Scanner(System.in);

    @Getter
    private User currentUser;

    public void registerUser() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        boolean success = userService.register(username, password);
        if (success) {
            System.out.println("User registered successfully");
        } else {
            System.out.println("Failed to register user");
        }
    }

    public void loginUser() {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        User user = userService.login(username, password);
        if (user != null) {
            currentUser = user;
            System.out.println("Login successful");
        } else {
            System.out.println("Invalid username or password");
        }
    }

    public void promoteUserToAdmin() {
        if (currentUser != null && "ADMIN".equals(currentUser.getRole())) {
            System.out.println("Enter username to promote:");
            String username = scanner.nextLine();
            userService.promoteUser(username);
            System.out.println(username + " promoted to admin");
        } else {
            System.out.println("Only ADMIN users can promote other users");
        }
    }

    public void demoteUserToUser() {
        if (currentUser != null && "ADMIN".equals(currentUser.getRole())) {
            System.out.println("Enter username to demote:");
            String username = scanner.nextLine();
            userService.demoteUser(username);
            System.out.println(username + " demoted to user");
        } else {
            System.out.println("Only ADMIN users can demote other users");
        }
    }

    public void showAllUsers() {
        List<User> users = userService.showAllUsers();
        System.out.println("All users:");
        for (User user : users) {
            System.out.println(user.getUsername() + " - " + user.getRole());
        }
    }

    public void logoutUser() {
        // Implementare logout dacă e necesar, în funcție de context
    }

}
