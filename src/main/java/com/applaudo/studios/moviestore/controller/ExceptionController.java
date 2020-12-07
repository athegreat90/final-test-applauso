package com.applaudo.studios.moviestore.controller;

import com.applaudo.studios.moviestore.dto.ResponseGenericDto;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.UnexpectedTypeException;

@RestControllerAdvice
public class ExceptionController
{
    public static final String FAILED = "Failed";
    Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ResponseGenericDto<String> manageAccessDeniedException(AccessDeniedException ex, WebRequest request)
    {
        logger.error("ERROR AccessDeniedException", ex);
        return new ResponseGenericDto<>(1, FAILED, "Unauthorized");
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseGenericDto<String> manageUnexpectedException(NotFoundException ex, WebRequest request)
    {
        logger.error("ERROR UnexpectedTypeException", ex);
        return new ResponseGenericDto<>(1, FAILED, ex.getMessage());
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
    public ResponseGenericDto<String> manageUnexpectedException(UnexpectedTypeException ex, WebRequest request)
    {
        logger.error("ERROR UnexpectedTypeException", ex);
        return new ResponseGenericDto<>(1, FAILED, ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
    public ResponseGenericDto<String> manageUnknownError(Exception ex, WebRequest request)
    {
        logger.error("ERROR Exception", ex);
        return new ResponseGenericDto<>(1, FAILED, ex.getMessage());
    }
}
