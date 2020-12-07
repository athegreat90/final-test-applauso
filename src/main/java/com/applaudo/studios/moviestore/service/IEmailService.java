package com.applaudo.studios.moviestore.service;

public interface IEmailService
{
    public void sendSimpleMessage(String to, String subject, String text, String from);
}
