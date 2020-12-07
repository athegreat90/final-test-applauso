package com.applaudo.studios.moviestore.controller;

import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.context.request.WebRequest;

import javax.validation.UnexpectedTypeException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExceptionControllerTest
{
    @Autowired
    ExceptionController exceptionController;

    @Test
    void manageAccessDeniedException()
    {
        var exception = Mockito.mock(AccessDeniedException.class);
        var webReq = Mockito.mock(WebRequest.class);
        assertNotNull(exceptionController.manageAccessDeniedException(exception, webReq));
    }

    @Test
    void manageUnexpectedException()
    {
        var exception = Mockito.mock(NotFoundException.class);
        var webReq = Mockito.mock(WebRequest.class);
        assertNotNull(exceptionController.manageUnexpectedException(exception, webReq));
    }

    @Test
    void testManageUnexpectedException()
    {
        var exception = Mockito.mock(UnexpectedTypeException.class);
        var webReq = Mockito.mock(WebRequest.class);
        assertNotNull(exceptionController.manageUnexpectedException(exception, webReq));
    }

    @Test
    void manageUnknownError()
    {
        var exception = Mockito.mock(Exception.class);
        var webReq = Mockito.mock(WebRequest.class);
        assertNotNull(exceptionController.manageUnknownError(exception, webReq));
    }
}