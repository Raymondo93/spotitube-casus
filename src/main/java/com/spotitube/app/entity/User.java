package com.spotitube.app.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table
public class User {

    @Id
    private String username;
    private String password;
    private String token;
//    @ManyToMany(targetEntity=Playlist.class)
//    private Set playlists;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    public Set getPlaylists() {
//        return playlists;
//    }
//
//    public void setPlaylists(Set playlists) {
//        this.playlists = playlists;
//    }
}
