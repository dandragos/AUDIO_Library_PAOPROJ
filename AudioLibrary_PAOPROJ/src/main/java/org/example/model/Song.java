package org.example.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Song implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int idCounter = 0;

    @Setter
    @Getter
    private int id;
    private String artistName;
    private String songName;
    private String albumName;
    private String releaseYear;

    public Song(String artistName, String songName, String albumName, String releaseYear) {
        this.id =++idCounter;
        this.artistName = artistName;
        this.songName = songName;
        this.albumName = albumName;
        this.releaseYear = releaseYear;
    }



    public int getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", artistName='" + artistName + '\'' +
                ", songName='" + songName + '\'' +
                ", albumName='" + albumName + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                '}';
    }
}
