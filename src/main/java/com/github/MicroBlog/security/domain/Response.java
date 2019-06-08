package com.github.MicroBlog.security.domain;

import java.io.Serializable;

public class Response implements Serializable {

    private static final long serialVersionUID = 3220177352398498436L;

    private String message;

    public Response(String message) {
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
