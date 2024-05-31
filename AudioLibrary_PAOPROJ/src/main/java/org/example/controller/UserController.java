package org.example.controller;



import lombok.Getter;
import lombok.Setter;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;

import java.util.Scanner;

public class UserController {
    private final UserService userService;
    @Getter
    @Setter
    private boolean loggedIn;

    public UserController() {
        this.userService = new UserServiceImpl();
        this.loggedIn = false;
    }


    public void promoteUser(Scanner scanner) {
        if (!loggedIn) {
            System.out.println("You must login first.");
            return;
        }
        System.out.print("Enter username to promote: ");
        String username = scanner.nextLine();
        userService.promoteUser(username);
        System.out.println("User promoted to ADMIN role.");
    }

    public void demoteUser(Scanner scanner) {
        if (!loggedIn) {
            System.out.println("You must login first.");
            return;
        }
        System.out.print("Enter username to demote: ");
        String username = scanner.nextLine();
        userService.demoteUser(username);
        System.out.println("User demoted to USER role.");
    }

    public void showAllUsers() {
        if (!loggedIn) {
            System.out.println("You must login first.");
            return;
        }
        userService.showAllUsers();
    }

    public void logout() {
        if (!loggedIn) {
            System.out.println("You are not logged in.");
            return;
        }
        loggedIn = false;
        System.out.println("Logout successful!");
    }

    public boolean isLoggedIn() {
        return userService.isLoggedIn();
    }

}