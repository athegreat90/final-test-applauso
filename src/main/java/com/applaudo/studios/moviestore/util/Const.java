package com.applaudo.studios.moviestore.util;

public class Const
{
    public static final String SIGNING_KEY = "final-4pp1aus0";
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = 5L * 60L * 60L;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String AUTHORITIES_KEY = "scopes";

    private Const()
    {
        //Default
    }
}
