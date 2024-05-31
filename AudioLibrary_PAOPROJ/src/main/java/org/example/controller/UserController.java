package org.example.controller;

import lombok.Getter;
import org.example.audit.AuditEntry;
import org.example.audit.AuditLogger;
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
            AuditLogger.log(username, "register", "User registered successfully");
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
            AuditLogger.log(username, "login", "Login successful");
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
            AuditLogger.log(currentUser.getUsername(), "promote", username + " promoted to admin");
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
            AuditLogger.log(currentUser.getUsername(), "demote", username + " demoted to user");
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

        // Audit log pentru afișarea tuturor utilizatorilor
        AuditLogger.log(currentUser.getUsername(), "showAllUsers", "Displayed all users");
    }

    public void logoutUser() {
        if (currentUser != null) {
            // Audit log pentru deconectarea utilizatorului
            AuditLogger.log(currentUser.getUsername(), "logout", "User logged out");
            currentUser = null;
            System.out.println("Logout successful");
        } else {
            System.out.println("No user logged in");
        }
    }


    public void auditUserCommands() {
        if (currentUser != null && "ADMIN".equals(currentUser.getRole())) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter username to audit:");
            String username = scanner.nextLine();
            int page = 1;
            int pageSize = 5;

            while (true) {
                // Obține înregistrările de audit pentru pagina specificată
                List<AuditEntry> auditEntries = AuditLogger.readAuditEntriesByUsername(username, page, pageSize);

                // Afișează înregistrările de audit
                System.out.println("Audit log for user " + username + ", page " + page + ":");
                for (AuditEntry entry : auditEntries) {
                    System.out.println(entry);
                }

                // Afisam meniul pentru paginare
                System.out.println("\n1. Next page");
                System.out.println("2. Previous page");
                System.out.println("3. Exit to main menu");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Consumă newline

                switch (option) {
                    case 1:
                        page++;
                        break;
                    case 2:
                        if (page > 1) {
                            page--;
                        } else {
                            System.out.println("You are already on the first page.");
                        }
                        break;
                    case 3:
                        return; // Ne întoarcem la meniul principal
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Only ADMIN users can audit user commands");
        }
    }



}
