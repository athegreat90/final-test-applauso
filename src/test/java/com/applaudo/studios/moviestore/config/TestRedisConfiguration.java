package com.applaudo.studios.moviestore.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.TestConfiguration;
import redis.embedded.RedisServer;
import redis.embedded.RedisServerBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Random;

@TestConfiguration
public class TestRedisConfiguration
{
    private RedisServer redisServer;

    public TestRedisConfiguration() {
        this.redisServer = RedisServer.builder().port(new Random().nextInt(7000)).setting("maxheap 2gb").build();
        System.out.println(this.redisServer.isActive());
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}
