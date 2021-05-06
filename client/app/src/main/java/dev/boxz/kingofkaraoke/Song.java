package dev.boxz.kingofkaraoke;

import java.io.Serializable;

public class Song implements Serializable {
    private String song;
    private String singer;
    private String album;
    private String coverURL;
    private String songURL;

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public String getSongURL() {
        return songURL;
    }

    public void setSongURL(String songURL) {
        this.songURL = songURL;
    }

    public Song(String song, String singer, String album, String coverURL, String songURL) {
        this.song = song;
        this.singer = singer;
        this.album = album;
        this.coverURL = coverURL;
        this.songURL = songURL;
    }
}
