package com.spotitube.app.model.src;

import com.spotitube.app.model.IPlaylistModel;
import com.spotitube.app.model.ITrackModel;

import javax.enterprise.inject.Default;
import java.util.List;

@Default
public class PlaylistModel implements IPlaylistModel {

    private int id;
    private String name;
    private String ownerName;
    private boolean owner;
    private int playlistLength;

    public PlaylistModel() {

    }

    public PlaylistModel(int id, String name, String owner, int playlistLength) {
        this.id = id;
        this.name = name;
        this.ownerName = owner;
        this.playlistLength = playlistLength;
    }

    public PlaylistModel(int id, String name, boolean owner, int playlistLength){
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.playlistLength = playlistLength;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String owner) {
        this.ownerName = owner;
    }

    public int getPlaylistLength() {
        return playlistLength;
    }

    public void setPlaylistLength(int playlistLength) {
        this.playlistLength = playlistLength;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
