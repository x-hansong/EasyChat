package com.easychat.controller.exception;

import com.easychat.model.ErrorInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by yonah on 15-11-6.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    static Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
    public ErrorInfo handleNotFound(HttpServletRequest req, NotFoundException ex){
        logger.info("{} not found", req.getRequestURI());
        return new ErrorInfo(ex.getError(), ex.getDescription());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    @ResponseBody
    public ErrorInfo handleBadRequest(HttpServletRequest req, BadRequestException ex) {
        logger.info("{} bad request", req.getRequestURI());
        return new ErrorInfo(ex.getError(), ex.getDescription());
    }
}
