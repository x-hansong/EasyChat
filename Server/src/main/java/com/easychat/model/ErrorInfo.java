package com.easychat.model;

/**
 * Created by yonah on 15-11-6.
 */
public class ErrorInfo {
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

    public ErrorInfo(String error, String description) {
        this.error = error;
        this.description = description;
    }
}
