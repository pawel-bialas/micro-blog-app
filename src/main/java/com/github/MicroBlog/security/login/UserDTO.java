package com.github.MicroBlog.security.login;

public class UserDTO {

    private String userLogin;
    private String token;

    public UserDTO(String userLogin, String token) {
        this.userLogin = userLogin;
        this.token = token;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
