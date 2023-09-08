package com.example.TestNav2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Data
@Component
@ConfigurationProperties(prefix = "db")
public class DbConfig {
    String driver;
    String url;
    String username;
    String password;
}
