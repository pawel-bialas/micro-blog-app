package com.github.MicroBlog.security.login;

import com.github.MicroBlog.model.Account;

public class AccountDTO {

    private String userLogin;
    private String token;

    public AccountDTO(String userLogin, String token) {
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
