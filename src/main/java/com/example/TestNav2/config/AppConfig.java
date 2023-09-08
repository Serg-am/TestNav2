package com.example.TestNav2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;;

@Configuration
public class AppConfig {
    private final DbConfig config;

    public AppConfig(DbConfig config) {
        this.config = config;
    }

    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(config.driver);
        dataSource.setUrl(config.url);
        dataSource.setUsername(config.username);
        dataSource.setPassword(config.password);
        return dataSource;
    }


    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
