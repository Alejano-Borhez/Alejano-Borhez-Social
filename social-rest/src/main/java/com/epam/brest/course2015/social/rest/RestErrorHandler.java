package com.epam.brest.course2015.social.rest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by alexander on 12.11.15.
 */
@ControllerAdvice
public class RestErrorHandler {

    private static final Logger LOGGER = LogManager.getLogger();

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleDataAccessException(
                                DataAccessException ex) {
        LOGGER.debug("Handling DataAccessException: " + ex);
        return "DataAccessException: " + ex.getLocalizedMessage();
    }
}