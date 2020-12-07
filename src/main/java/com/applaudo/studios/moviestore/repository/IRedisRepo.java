package com.applaudo.studios.moviestore.repository;

public interface IRedisRepo
{
    public Boolean saveKey(String key, String content, Long duration);

    public String getKey(String key);

    public Boolean deleteKey(String key);
}
