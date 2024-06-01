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
                scanner.nextLine();
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

                    case 7:
                        userController.createSong();
                        break;


                    case 8:
                        playlistmenu();
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
                        userController.searchSong();
                        break;
                    case 2:
                        userController.logoutUser();
                        loggedIn = false;
                        break;

                    case 3:
                        playlistmenu();
                        break;

                    case 4:
                        userController.showAllSongs();
                        break;
                    case 5:
                        exit=true;
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
        System.out.println("2. Demote ADMIN to User");
        System.out.println("3. Show all users");
        System.out.println("4. Read User audit");
        System.out.println("5. Logout");
        System.out.println("6. Playlists");
        System.out.println("7. Create song");
        System.out.println("8. Playlists");
        System.out.println("Choose an option:");
    }

    private static void printUserMenu() {
        System.out.println("Welcome to the USER menu!");
        System.out.println("1. Search song");
        System.out.println("2. Logout");
        System.out.println("3. Playlist");
        System.out.println("4. Show all songs");
        System.out.println("5. Exit");
        System.out.println("Choose an option:");
    }

    private static void printPlaylistMenu() {
        System.out.println("Playlist menu:");
        System.out.println("1. Show your playlists");
        System.out.println("2. Create new playlist");
        System.out.println("3. Add songs to your playlist");
        System.out.println("4. Export your playlis");
        System.out.println("5. Delete a playlist");
        System.out.println("5. Exit");
        System.out.println("Choose an option:");
    }



    private static String userRole (){
        User currentUser = userController.getCurrentUser();
        return currentUser.getRole();
    }

    private static void playlistmenu(){
        printPlaylistMenu();
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option){
            case 1:
                userController.showUserPlaylists();
                break;
            case 2:
                userController.createPlaylist();
                break;
            case 3:
                userController.addToPlaylist();
                break;

            case 4:
                userController.exportPlaylist();
                break;

            case 5:
                userController.deletePlaylist();
                break;

            default:
                System.out.println("Invalid Option!");
        }
    }


}
