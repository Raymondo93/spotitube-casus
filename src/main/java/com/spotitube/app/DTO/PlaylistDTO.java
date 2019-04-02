package com.spotitube.app.DTO;


import com.spotitube.app.model.src.TrackModel;

public class PlaylistDTO {

    private int id;
    private String name;
    private String owner;
    private TrackModel[] tracks;

    public PlaylistDTO() {

    }

    public PlaylistDTO(int id, String name, String owner, TrackModel[] tracks){
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public TrackModel[] getTracks() {
        return tracks;
    }

    public void setTracks(TrackModel[] tracks) {
        this.tracks = tracks;
    }
}
