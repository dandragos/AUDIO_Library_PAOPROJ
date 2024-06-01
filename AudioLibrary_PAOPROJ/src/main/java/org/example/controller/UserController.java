package org.example.controller;

import lombok.Getter;
import org.example.audit.AuditEntry;
import org.example.audit.AuditLogger;
import org.example.model.Playlist;
import org.example.model.Song;
import org.example.model.User;
import org.example.service.PlaylistService;
import org.example.service.SongService;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;

import java.util.List;
import java.util.Scanner;

public class UserController {

    private final UserService userService = new UserServiceImpl();
    private final Scanner scanner = new Scanner(System.in);

    private final SongService songService = new SongService();

    private final PlaylistService playlistService = new PlaylistService();



    public void createPlaylist() {
        if (currentUser != null) {
            System.out.println("Enter playlist name:");
            String playlistName = scanner.nextLine();
            playlistService.createPlaylist(currentUser.getUsername(), playlistName);
            System.out.println("Playlist created successfully.");
            AuditLogger.log(currentUser.getUsername(), "create playlist", "Created playlist " + playlistName);
        } else {
            System.out.println("No user logged in.");
        }
    }

    public void showUserPlaylists() {
        if (currentUser != null) {
            List<Playlist> playlists = playlistService.getUserPlaylists(currentUser.getUsername());
            AuditLogger.log(currentUser.getUsername(), "Show user playlist", "Showed all playlists");
            if (playlists.isEmpty()) {
                System.out.println("No playlists found.");
                return;
            }

            int currentPage = 1;
            int pageSize = 5;
            boolean continuePaging = true;

            while (continuePaging) {
                int startIndex = (currentPage - 1) * pageSize;
                int endIndex = Math.min(startIndex + pageSize, playlists.size());

                System.out.println("Your playlists, page " + currentPage + ":");
                for (int i = startIndex; i < endIndex; i++) {
                    System.out.println((i + 1) + ". " + playlists.get(i).getName());
                }

                System.out.println("Options: [N]ext page, [P]revious page, [S]elect playlist, [E]xit to main menu");
                String option = scanner.nextLine();

                switch (option.toLowerCase()) {
                    case "n":
                        if (endIndex < playlists.size()) {
                            currentPage++;
                        } else {
                            System.out.println("No more pages.");
                        }
                        break;
                    case "p":
                        if (currentPage > 1) {
                            currentPage--;
                        } else {
                            System.out.println("This is the first page.");
                        }
                        break;
                    case "s":
                        System.out.println("Enter the number of the playlist to view:");
                        int playlistNumber = scanner.nextInt();
                        scanner.nextLine();
                        if (playlistNumber > 0 && playlistNumber <= playlists.size()) {
                            showPlaylistSongs(playlists.get(playlistNumber - 1));
                        } else {
                            System.out.println("Invalid playlist number.");
                        }
                        break;
                    case "e":
                        continuePaging = false;
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } else {
            System.out.println("No user logged in.");
        }
    }

    private void showPlaylistSongs(Playlist playlist) {
        List<Song> songs = playlist.getSongs();
        if (songs.isEmpty()) {
            System.out.println("No songs in this playlist.");
            return;
        }

        int currentPage = 1;
        int pageSize = 5;
        boolean continuePaging = true;

        while (continuePaging) {
            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, songs.size());

            System.out.println("Songs in playlist " + playlist.getName() + ", page " + currentPage + ":");
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println(songs.get(i));
            }

            System.out.println("Options: [N]ext page, [P]revious page, [E]xit to main menu");
            String option = scanner.nextLine();

            switch (option.toLowerCase()) {
                case "n":
                    if (endIndex < songs.size()) {
                        currentPage++;
                    } else {
                        System.out.println("No more pages.");
                    }
                    break;
                case "p":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("This is the first page.");
                    }
                    break;
                case "e":
                    continuePaging = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public void addToPlaylist() {
        if (currentUser != null) {
            System.out.println("Enter playlist name:");
            String playlistName = scanner.nextLine();
            System.out.println("Enter song ID to add:");
            int songId = scanner.nextInt();
            scanner.nextLine();

            Song song = songService.getSongById(songId);
            if (song != null) {
                playlistService.addSongToPlaylist(currentUser.getUsername(), playlistName, song);
                System.out.println("Song added to playlist successfully.");
                AuditLogger.log(currentUser.getUsername(), "Added song (ID): ", songService.getSongById(songId) + "to" + playlistName);
            } else {
                System.out.println("Song not found.");
            }


        } else {
            System.out.println("No user logged in.");
        }
    }

    public void deletePlaylist() {
        if (currentUser != null) {
            System.out.println("Enter the name of the playlist to delete:");
            String playlistName = scanner.nextLine();
            playlistService.deletePlaylist(currentUser.getUsername(), playlistName);
            AuditLogger.log(currentUser.getUsername(), "Delete playlist", "Deleted playlist " + playlistName);
        } else {
            System.out.println("No user logged in.");
        }
    }

    public void exportPlaylist() {
        if (currentUser != null) {
            System.out.println("Enter playlist name to export:");
            String playlistName = scanner.nextLine();
            playlistService.exportPlaylistToTxt(currentUser.getUsername(), playlistName);
            AuditLogger.log(currentUser.getUsername(), "Export playlist", "Exported playlist " + playlistName);

        } else {
            System.out.println("No user logged in.");
        }
    }


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
    public void searchSong() {
        System.out.println("Choose search criterion:");
        System.out.println("1. Artist");
        System.out.println("2. Song");
        System.out.println("3. Year");
        System.out.println("4. Album");
        System.out.println("5. ID");
        AuditLogger.log(currentUser.getUsername(), "Searched for song", "songs displayed");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String criterion;
        switch (choice) {
            case 1 -> criterion = "artist";
            case 2 -> criterion = "song";
            case 3 -> criterion = "year";
            case 4 -> criterion = "album";
            case 5 -> criterion = "id";
            default -> {
                System.out.println("Invalid choice. Returning to main menu.");
                return;
            }
        }

        System.out.println("Enter the value to search for:");
        String value = scanner.nextLine();

        int pageSize = 5;
        int currentPage = 1;
        boolean keepSearching = true;

        while (keepSearching) {
            List<Song> results = songService.searchSongs(criterion, value);
            if (results.isEmpty()) {
                System.out.println("No songs found matching the criteria.");
                return;
            }

            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, results.size());

            System.out.println("Songs found (Page " + currentPage + "):");
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println(results.get(i));
            }

            System.out.println("\nOptions:");
            System.out.println("1. Next page");
            System.out.println("2. Previous page");
            System.out.println("3. Back to main menu");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    if (endIndex < results.size()) {
                        currentPage++;
                    } else {
                        System.out.println("You are on the last page.");
                    }
                }
                case 2 -> {
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("You are on the first page.");
                    }
                }
                case 3 -> keepSearching = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void showAllSongs() {
        int pageSize = 5;
        int currentPage = 1;
        boolean keepShowing = true;

        AuditLogger.log(currentUser.getUsername(), "Show all songs", "Showed all songs " );

        while (keepShowing) {
            List<Song> allSongs = songService.getAllSongs();
            if (allSongs.isEmpty()) {
                System.out.println("No songs available.");
                return;
            }

            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, allSongs.size());

            System.out.println("All songs (Page " + currentPage + "):");
            for (int i = startIndex; i < endIndex; i++) {
                System.out.println(allSongs.get(i));
            }

            System.out.println("\nOptions:");
            System.out.println("1. Next page");
            System.out.println("2. Previous page");
            System.out.println("3. Back to main menu");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    if (endIndex < allSongs.size()) {
                        currentPage++;
                    } else {
                        System.out.println("You are on the last page.");
                    }
                }
                case 2 -> {
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("You are on the first page.");
                    }
                }
                case 3 -> keepShowing = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
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


        AuditLogger.log(currentUser.getUsername(), "showAllUsers", "Displayed all users");
    }

    public void logoutUser() {
        if (currentUser != null) {

            AuditLogger.log(currentUser.getUsername(), "logout", "User logged out");
            currentUser = null;
            System.out.println("Logout successful");
        } else {
            System.out.println("No user logged in");
        }
    }

    public void createSong() {
        if (currentUser != null && "ADMIN".equals(currentUser.getRole())) {
            System.out.println("Enter artist name:");
            String artistName = scanner.nextLine();

            System.out.println("Enter song name:");
            String songName = scanner.nextLine();

            System.out.println("Enter album name:");
            String albumName = scanner.nextLine();

            System.out.println("Enter release year:");
            String releaseYear = scanner.nextLine();

            Song newSong = new Song(artistName, songName, albumName, releaseYear);
            songService.saveSong(newSong);

            System.out.println("Song created successfully: " + newSong);
        } else {
            System.out.println("Only ADMIN users can create songs");
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

                List<AuditEntry> auditEntries = AuditLogger.readAuditEntriesByUsername(username, page, pageSize);


                System.out.println("Audit log for user " + username + ", page " + page + ":");
                for (AuditEntry entry : auditEntries) {
                    System.out.println(entry);
                }


                System.out.println("\n1. Next page");
                System.out.println("2. Previous page");
                System.out.println("3. Exit to main menu");
                System.out.print("Choose an option: ");
                int option = scanner.nextInt();
                scanner.nextLine();

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
                        return;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Only ADMIN users can audit user commands");
        }
    }



}
