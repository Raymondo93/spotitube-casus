package com.spotitube.app.DTO;

public class UserLoginDTO{

    private String user;
    private String password;

    public UserLoginDTO() {

    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
