package org.example;



import org.example.controller.LoginController;
import org.example.controller.RegisterController;
import org.example.controller.UserController;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserController userController = new UserController(); // Creare și inițializare UserController
        LoginController loginController = new LoginController(userController); // Transmiterea UserController către LoginController
        RegisterController registerController = new RegisterController(userController); // Transmiterea UserController către RegisterController

        boolean running = true;
        boolean loggedIn = userController.isLoggedIn();




        while (running) {
            System.out.println(loggedIn);
            if (!loggedIn) {
                // Meniu pentru utilizatorii neautentificați
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consumă newline

                switch (option) {
                    case 1:
                        loginController.login(scanner);
                        loggedIn = userController.isLoggedIn();
                        break;
                    case 2:
                        registerController.register(scanner);
                        break;
                    case 3:
                        System.out.println("Exiting application...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } else {
                // Meniu pentru utilizatorii autentificați
                System.out.println("1. Logout");
                System.out.println("2. Promote user");
                System.out.println("3. Demote user");
                System.out.println("4. Show all users");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consumă newline

                switch (option) {
                    case 1:
                        userController.logout();
                        loggedIn = false; // Utilizatorul s-a deconectat
                        break;
                    case 2:
                        userController.promoteUser(scanner);
                        break;
                    case 3:
                        userController.demoteUser(scanner);
                        break;
                    case 4:
                        userController.showAllUsers();
                        break;
                    case 5:
                        System.out.println("Exiting application...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }
}