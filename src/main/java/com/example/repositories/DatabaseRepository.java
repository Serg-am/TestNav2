package com.example.repositories;

import com.example.config.DbConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class DatabaseRepository {
    private final JdbcTemplate jdbcTemplate;
    private final DbConfig config;

    @Autowired
    public DatabaseRepository(JdbcTemplate jdbcTemplate, DbConfig config) {
        this.jdbcTemplate = jdbcTemplate;
        this.config = config;
    }

    public void createDatabase(String dbName) {
        jdbcTemplate.execute("CREATE DATABASE " + dbName);
        log.info("БД " + dbName + " создана успешно!");
    }

    public void createDatabaseFromTemplate(String dbName, String templateName) {
        log.info("БД " + dbName + " создается...");
        jdbcTemplate.execute("CREATE DATABASE " + dbName + " TEMPLATE " + templateName);
        log.info("БД " + dbName + " создана по шаблону успешно!");
    }

    public void createTable(String dbName) {
        DriverManagerDataSource source = (DriverManagerDataSource) jdbcTemplate.getDataSource();
        source.setUrl(config.getUrl() + dbName);
        jdbcTemplate.setDataSource(source);

        jdbcTemplate.execute("CREATE TABLE table_name (column_name INT PRIMARY KEY)");

        log.info("Таблица " + dbName + " создана в БД успешно!");

        log.info("Наполнение таблицы...");
        for (int i = 1; i <= 100; i++) {
            jdbcTemplate.update("INSERT INTO table_name (column_name) VALUES (?)", i);
        }

        log.info("Значения вставлены в таблицу БД " + dbName + " успешно!");
    }
}
