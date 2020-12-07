package com.applaudo.studios.moviestore.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "redis")
@Data
public class RedisPropertiesCustom
{
    private String keyUpdate;
    private Long timeUpdate;
}
