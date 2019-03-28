package com.spotitube.app.model.src;

import com.spotitube.app.model.IUserModel;

import javax.enterprise.inject.Default;

@Default
public class UserModel implements IUserModel {

    private String username;
    private String password;
    private String token;

    public UserModel() {

    }
    public UserModel(String username, String password){
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
}
