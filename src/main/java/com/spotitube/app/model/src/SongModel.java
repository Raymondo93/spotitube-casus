package com.spotitube.app.model.src;

import javax.enterprise.inject.Default;
import java.util.Date;
public class SongModel extends TrackModel {

    private String album;

    public SongModel() {

    }

    public SongModel(int id, String performer, String title, int duration, String url,
                     int playcount, String album, boolean offlineAvailable) {
        super(id, performer, title, duration, url, playcount, offlineAvailable);
        this.album = album;
    }

    @Override
    public String getAlbum() {
        return this.album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

}
