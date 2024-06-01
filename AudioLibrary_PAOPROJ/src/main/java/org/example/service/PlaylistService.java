package org.example.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.Playlist;
import org.example.model.Song;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class PlaylistService {
    private static final String PLAYLISTS_FILE = "src/main/java/org/example/playlists/playlists.json";
    private Map<String, List<Playlist>> userPlaylists;

    public PlaylistService() {
        userPlaylists = new HashMap<>();
        loadPlaylists();
    }

    public void createPlaylist(String username, String playlistName) {
        Playlist newPlaylist = new Playlist(playlistName);
        userPlaylists.computeIfAbsent(username, k -> new ArrayList<>()).add(newPlaylist);
        savePlaylists();
    }

    public List<Playlist> getUserPlaylists(String username) {
        return userPlaylists.getOrDefault(username, new ArrayList<>());
    }

    public void addSongToPlaylist(String username, String playlistName, Song song) {
        List<Playlist> playlists = userPlaylists.get(username);
        if (playlists != null) {
            for (Playlist playlist : playlists) {
                if (playlist.getName().equals(playlistName)) {
                    playlist.addSong(song);
                    savePlaylists();
                    return;
                }
            }
        }
        System.out.println("Playlist not found.");
    }

    public void savePlaylists() {
        try (Writer writer = new FileWriter(PLAYLISTS_FILE)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(userPlaylists, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPlaylists() {
        File file = new File(PLAYLISTS_FILE);
        if (file.exists()) {
            try (Reader reader = new FileReader(file)) {
                Gson gson = new Gson();
                Type type = new TypeToken<Map<String, List<Playlist>>>() {}.getType();
                userPlaylists = gson.fromJson(reader, type);
                if (userPlaylists == null) {
                    userPlaylists = new HashMap<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void exportPlaylistToTxt(String username, String playlistName) {
        List<Playlist> playlists = userPlaylists.get(username);
        if (playlists != null) {
            for (Playlist playlist : playlists) {
                if (playlist.getName().equals(playlistName)) {
                    writePlaylistToTxt(playlist);
                    return;
                }
            }
        }
        System.out.println("Playlist not found.");
    }

    private void writePlaylistToTxt(Playlist playlist) {
        String fileName = "src/main/java/org/example/exports/" + playlist.getName() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Playlist: " + playlist.getName());
            writer.newLine();
            writer.write("Songs:");
            writer.newLine();
            for (Song song : playlist.getSongs()) {
                writer.write("ID: " + song.getId() + ", Artist: " + song.getArtistName() + ", Song: " + song.getSongName() + ", Album: " + song.getAlbumName() + ", Year: " + song.getReleaseYear());
                writer.newLine();
            }
            System.out.println("Playlist exported to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePlaylist(String username, String playlistName) {
        List<Playlist> playlists = userPlaylists.get(username);
        if (playlists != null) {
            boolean removed = playlists.removeIf(playlist -> playlist.getName().equals(playlistName));
            if (removed) {
                savePlaylists();
                System.out.println("Playlist " + playlistName + " has been deleted.");
            } else {
                System.out.println("Playlist not found.");
            }
        } else {
            System.out.println("Playlist not found.");
        }
    }
}
