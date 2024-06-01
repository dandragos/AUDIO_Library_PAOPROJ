package org.example.service;

import org.example.model.Song;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SongService {

    private static final List<Song> songs = new ArrayList<>();
    private static final String SONGS_DIR = "src/main/java/org/example/songs/";
    private static final String ID_COUNTER_FILE = "src/main/java/org/example/songs/id_counter.txt";

    public void saveSong(Song song) {

        File directory = new File(SONGS_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }


        int currentId = getCurrentId();
        song.setId(currentId);

        try (FileOutputStream fileOut = new FileOutputStream(SONGS_DIR + song.getId() + "_" + song.getSongName() + ".song");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(song);
        } catch (IOException e) {
            e.printStackTrace();
        }


        updateCurrentId(currentId + 1);
    }

    private int getCurrentId() {
        File idFile = new File(ID_COUNTER_FILE);
        if (!idFile.exists()) {
            return 1;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(idFile))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 1;
    }

    private void updateCurrentId(int newId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ID_COUNTER_FILE))) {
            writer.write(String.valueOf(newId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Song> searchSongs(String criterion, String value) {
        List<Song> matchingSongs = new ArrayList<>();
        File directory = new File(SONGS_DIR);
        if (!directory.exists()) {
            return matchingSongs;
        }

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".song"));
        if (files != null) {
            for (File file : files) {
                try (FileInputStream fileIn = new FileInputStream(file);
                     ObjectInputStream in = new ObjectInputStream(fileIn)) {
                    Song song = (Song) in.readObject();
                    if (matchesCriterion(song, criterion, value)) {
                        matchingSongs.add(song);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return matchingSongs;
    }

    private boolean matchesCriterion(Song song, String criterion, String value) {
        switch (criterion.toLowerCase()) {
            case "artist":
                return song.getArtistName().equalsIgnoreCase(value);
            case "song":
                return song.getSongName().equalsIgnoreCase(value);
            case "year":
                return song.getReleaseYear().equals(value);
            case "album":
                return song.getAlbumName().equalsIgnoreCase(value);
            case "id":
                return Integer.toString(song.getId()).equals(value);
            default:
                return false;
        }
    }


    public List<Song> getAllSongs() {
        List<Song> allSongs = new ArrayList<>();

        File directory = new File(SONGS_DIR);
        if (!directory.exists()) {
            return allSongs;
        }

        File[] files = directory.listFiles((dir, name) -> name.endsWith(".song"));
        if (files != null) {
            for (File file : files) {
                try (FileInputStream fileIn = new FileInputStream(file);
                     ObjectInputStream in = new ObjectInputStream(fileIn)) {
                    Song song = (Song) in.readObject();
                    allSongs.add(song);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        return allSongs;
    }

    public Song getSongById(int id) {
        File directory = new File(SONGS_DIR);
        if (directory.exists()) {
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".song"));
            if (files != null) {
                for (File file : files) {
                    try (FileInputStream fileIn = new FileInputStream(file);
                         ObjectInputStream in = new ObjectInputStream(fileIn)) {
                        Song song = (Song) in.readObject();
                        if (song.getId() == id) {
                            return song;
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }


}
