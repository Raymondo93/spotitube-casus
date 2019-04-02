package com.spotitube.app.DTO;

public class UserLoginResponseDTO {

    private String token;
    private String user;

    public UserLoginResponseDTO() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
