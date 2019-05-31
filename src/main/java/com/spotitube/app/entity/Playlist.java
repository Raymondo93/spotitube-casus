package com.spotitube.app.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@Table
public class Playlist {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int playlistId;
    private String playlistName;
    private String owner;
    @ManyToMany(targetEntity= User.class)
    private Set users;
    @ManyToMany(targetEntity= Track.class)
    private Set tracks;
    @Transient
    private int playtime;

    public Playlist() {
        super();
    }

    public Playlist(int playlistId, String playlistName, String owner) {
       super();
       this.playlistId = playlistId;
       this.playlistName = playlistName;
       this.owner = owner;
    }


    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int id) {
        this.playlistId = id;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String name) {
        this.playlistName = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getPlaytime() {
        return playtime;
    }

    public void setPlaytime(int playtime) {
        this.playtime = playtime;
    }

    public Set getUsers() {
        return users;
    }

    public void setUsers(Set users) {
        this.users = users;
    }

    public Set getTracks() {
        return tracks;
    }

    public void setTracks(Set tracks) {
        this.tracks = tracks;
    }
}
