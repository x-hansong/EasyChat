package com.easychat.controller.exception;

/**
 * Created by yonah on 15-11-6.
 */
public class BasicException extends Exception{
    private String error;
    private String description;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BasicException(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
