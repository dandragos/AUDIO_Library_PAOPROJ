package org.example.controller;



import org.example.service.UserService;
import org.example.service.UserServiceImpl;

import java.util.Scanner;

public class LoginController {
    private UserService userService;
    private UserController userController;

    public LoginController(UserController userController) {
        this.userService = new UserServiceImpl();
        this.userController = userController; // Setăm userController-ul primit ca parametru
    }

    public void login(Scanner scanner) {
        if (userController.isLoggedIn()) {
            System.out.println("You are already logged in.");
            return;
        }
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userService.login(username, password) != null) {
            userController.setLoggedIn(true);
            System.out.println("Login successful!");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }
}