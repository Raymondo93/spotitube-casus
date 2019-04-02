package com.spotitube.app.DTO;



public class PlaylistResponseDTO {

    private PlaylistDTO[] playlists;
    private int length;

    public PlaylistResponseDTO() {

    }

    public PlaylistResponseDTO(PlaylistDTO[] playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    public PlaylistDTO[] getPlaylists() {
       return playlists;
    }

    public void setPlaylists(PlaylistDTO[] playlists) {
        this.playlists = playlists;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
