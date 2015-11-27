package com.easychat.exception;

/**
 * Created by yonah on 15-11-6.
 */
public class BadRequestException extends BasicException{
    public BadRequestException(String error, String description) {
        super(error, description);
    }
}
