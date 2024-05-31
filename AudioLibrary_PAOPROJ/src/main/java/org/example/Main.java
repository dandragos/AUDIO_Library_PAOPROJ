package org.example;

import org.example.controller.UserController;
import org.example.model.User;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final UserController userController = new UserController();
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean loggedIn = false;

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            if (!loggedIn) {
                printLoginMenu();
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (option) {
                    case 1:
                        userController.registerUser();
                        break;
                    case 2:
                        userController.loginUser();
                        loggedIn = true;
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } else if (Objects.equals(userRole(), "ADMIN"))
            {

                printAdminMenu();
                int option = scanner.nextInt();
                scanner.nextLine();
                switch(option){
                    case 1:
                        userController.promoteUserToAdmin();
                        break;
                    case 2:
                        userController.demoteUserToUser();
                        break;
                    case 3:
                        userController.showAllUsers();
                        break;
                    case 4:
                        userController.auditUserCommands();
                        break;
                    case 5:
                        userController.logoutUser();
                        loggedIn = false;
                        break;
                    case 6:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option");

                }

            } else {

                printUserMenu();
                int option = scanner.nextInt();
                scanner.nextLine();
                switch (option){
                    case 1:
                        userController.showAllUsers();
                        break;
                    case 2:
                        userController.logoutUser();
                        loggedIn = false;
                        break;


                    case 3:
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid option");
                }

            }
        }
        scanner.close();
    }

    private static void printLoginMenu() {
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("Choose an option:");
    }

    private static void printAdminMenu() {
        System.out.println("Welcome to the admin menu!");
        System.out.println("1. Promote User to ADMIN");
        System.out.println("2. Demote ADMIN to uSER");
        System.out.println("3. Show all users");
        System.out.println("4. Read User audit");
        System.out.println("5. Logout");
        System.out.println("6. Exit");
        System.out.println("Choose an option:");
    }

    private static void printUserMenu() {
        System.out.println("Welcome to the USER menu!");
        System.out.println("1. Show all users");
        System.out.println("2. Logout");
        System.out.println("3. Exit");
        System.out.println("Choose an option:");
    }

    private static String userRole (){
        User currentUser = userController.getCurrentUser();
        return currentUser.getRole();
    }


}
