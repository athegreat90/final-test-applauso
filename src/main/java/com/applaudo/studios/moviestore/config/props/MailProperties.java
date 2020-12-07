package com.applaudo.studios.moviestore.config.props;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailProperties
{
    private String host;
    private Integer port;
    private String username;
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailSmtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String startTlsEnable;

    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String transportProtocol;

    @Value("${spring.mail.properties.mail.smtp.port:25}")
    private Integer mailPort;

    @Value("${spring.mail.properties.mail.smtp.starttls.required:true}")
    private String startTlsRequired;

    @Value("${spring.mail.debug:true}")
    private String debug;

    private String from;


    private String forgotTemplate;

    private String urlResponse;
}
