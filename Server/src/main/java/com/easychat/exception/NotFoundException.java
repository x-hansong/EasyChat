package com.easychat.exception;

/**
 * Created by yonah on 15-11-6.
 */
public class NotFoundException extends BasicException{

    public NotFoundException(String error, String description) {

        super(error, description);
    }
}
