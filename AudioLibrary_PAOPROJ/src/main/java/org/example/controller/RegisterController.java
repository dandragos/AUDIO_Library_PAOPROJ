package org.example.controller;


import org.example.service.UserService;
import org.example.service.UserServiceImpl;

import java.util.Scanner;

public class RegisterController {
    private UserService userService;
    private UserController userController;

    public RegisterController(UserController userController) {
        this.userService = new UserServiceImpl();
        this.userController = userController; // Setăm userController-ul primit ca parametru
    }

    public void register(Scanner scanner) {
        if (userController.isLoggedIn()) {
            System.out.println("You are already logged in.");
            return;
        }
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (userService.register(username, password)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Username may already be taken.");
        }
    }
}