package com.example.TestNav2.DAO;

import com.example.TestNav2.config.DbConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class DatabaseDAO {
    private final JdbcTemplate jdbcTemplate;
    private final DbConfig config;

    @Autowired
    public DatabaseDAO(JdbcTemplate jdbcTemplate, DbConfig config) {
        this.jdbcTemplate = jdbcTemplate;
        this.config = config;
    }

    public void createDatabase(String dbName) {
        jdbcTemplate.execute("CREATE DATABASE " + dbName);
        System.out.println("БД " + dbName + " создана успешно!");
    }

    public void createDatabaseFromTemplate(String dbName, String templateName) {
        System.out.println("БД " + dbName + " создается...");

        jdbcTemplate.execute("CREATE DATABASE " + dbName + " TEMPLATE " + templateName);
        System.out.println("БД " + dbName + " создана по шаблону успешно!");
    }

    public void createTable(String dbName) {
        DriverManagerDataSource source = (DriverManagerDataSource) jdbcTemplate.getDataSource();
        source.setUrl(config.getUrl() + dbName);
        jdbcTemplate.setDataSource(source);

        jdbcTemplate.execute("CREATE TABLE table_name (column_name INT PRIMARY KEY)");
        System.out.println("Таблица " + dbName + " создана в БД успешно!");


        for (int i = 1; i <= 100; i++) {
            jdbcTemplate.update("INSERT INTO table_name (column_name) VALUES (?)", i);
        }
        System.out.println("Значения вставлены в таблицу БД " + dbName + " успешно!");
    }
}
