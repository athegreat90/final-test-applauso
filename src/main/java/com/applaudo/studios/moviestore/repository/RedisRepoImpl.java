package com.applaudo.studios.moviestore.repository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RedisRepoImpl implements IRedisRepo
{
    public static final String REDIS_ERROR = "REDIS_ERROR";

    private final RedisTemplate<String, Object> redisTemplate;


    @Override
    public Boolean saveKey(String key, String content, Long duration)
    {
        Boolean result = Boolean.FALSE;
        try
        {
            log.debug("Redis Values - Key: {} - Content: {} - Duration: {}", key, content, duration);
            redisTemplate.opsForValue().set(key, content);
            redisTemplate.expire(key, duration.intValue(), TimeUnit.SECONDS);

            result = Boolean.TRUE;
        }
        catch (Exception ex)
        {
            log.error(REDIS_ERROR, ex);
        }
        return result;
    }

    @Override
    public String getKey(String key)
    {
        String result = "";
        try
        {
            result = (String) redisTemplate.opsForValue().get(key);
            log.debug("Redis Values - Key: {} - Content: {}", key, result);
        }
        catch (Exception ex)
        {
            log.error(REDIS_ERROR, ex);
        }
        return result;
    }

    @Override
    public Boolean deleteKey(String key)
    {
        Boolean result = Boolean.FALSE;
        try
        {
            result = redisTemplate.delete(key);
        }
        catch (Exception ex)
        {
            log.error(REDIS_ERROR, ex);
        }
        return result;
    }
}
