package com.spotitube.app.DTO;

public class PlaylistDTO {

    private int id;
    private String name;
    private boolean owner;
    private String ownerName;
    private TrackDTO[] tracks;
    private int playlistLength;

    public PlaylistDTO() {

    }

    public PlaylistDTO(int id, String name, boolean owner, String ownerName, TrackDTO[] tracks, int playlistLength){
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.ownerName = ownerName;
        this.tracks = tracks;
        this.playlistLength = playlistLength;
    }

    public PlaylistDTO(int id, String name, String ownerName, int playlistLength) {
        this.id = id;
        this.name = name;
        this.ownerName = ownerName;
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

    public TrackDTO[] getTracks() {
        return tracks;
    }

    public void setTracks(TrackDTO[] tracks) {
        this.tracks = tracks;
    }

    public int getPlaylistLength() {
        return playlistLength;
    }

    public void setPlaylistLength(int playlistLength) {
        this.playlistLength = playlistLength;
    }

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
