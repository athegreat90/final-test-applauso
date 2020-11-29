package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.UnexpectedTypeException;

@RestControllerAdvice
public class ExceptionController
{
    Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    public ResponseGenericDto<String> manageUnexpectedException(UnexpectedTypeException ex, WebRequest request)
    {
        logger.error("ERROR UnexpectedTypeException", ex);
        return new ResponseGenericDto<>(1, "Failed", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    public ResponseGenericDto<String> manageUnknownError(Exception ex, WebRequest request)
    {
        logger.error("ERROR Exception", ex);
        return new ResponseGenericDto<>(1, "Failed", ex.getMessage());
    }
}
