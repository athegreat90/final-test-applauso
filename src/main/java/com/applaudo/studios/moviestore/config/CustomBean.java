package com.applaudo.studios.moviestore.config;

import com.applaudo.studios.moviestore.config.props.MailProperties;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;

@Configuration
public class CustomBean
{
    private static final Logger logger = LoggerFactory.getLogger(CustomBean.class);

    private final MailProperties mailProperties;

    private final RedisProperties redisProperties;

    public CustomBean(MailProperties mailProperties, RedisProperties redisProperties)
    {
        this.mailProperties = mailProperties;
        this.redisProperties = redisProperties;
    }

    @Bean
    public BCryptPasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper getModelMapper()
    {
        return new ModelMapper();
    }

    @Bean
    public JavaMailSender getJavaMailSender()
    {
        logger.debug("Mail Props: {}", mailProperties);

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailProperties.getHost());
        mailSender.setPort(mailProperties.getPort());
        mailSender.setUsername(mailProperties.getUsername());
        mailSender.setPassword(mailProperties.getPassword());

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailProperties.getTransportProtocol());
        props.put("mail.smtp.auth", mailProperties.getMailSmtpAuth());
        props.put("mail.smtp.starttls.enable", mailProperties.getStartTlsEnable());
        props.put("mail.debug", mailProperties.getDebug());

        return mailSender;
    }

    @Bean
    JedisConnectionFactory jedisConnectionFactory()
    {
        logger.debug("Redis Host: {}", redisProperties.getHost());
        logger.debug("Redis Port: {}", redisProperties.getPort());
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
        redisStandaloneConfiguration.setPassword(redisProperties.getPassword());
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    RedisTemplate<String, Object> redisTemplate()
    {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        logger.debug("RedisTemplate: {}", redisTemplate);
        return redisTemplate;
    }

}
