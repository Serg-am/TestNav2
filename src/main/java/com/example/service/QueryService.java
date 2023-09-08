package com.example.service;

import com.example.config.DbConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
@Slf4j
public class QueryService {
    private final JdbcTemplate jdbcTemplate;
    private final DbConfig config;

    @Autowired
    public QueryService(JdbcTemplate jdbcTemplate, DbConfig config) {
        this.jdbcTemplate = jdbcTemplate;
        this.config = config;
    }

    public void performQueries(String threadName) {
        Random random = new Random();
        int count = 0;
        while (count < 10) {
            String dbName = "db_" + (random.nextInt(3) + 1);
            int value = random.nextInt(100) + 1;
            queryRandomValue(dbName, value, threadName);
            count++;
        }
    }

    private void queryRandomValue(String dbName, int value, String threadName) {
        DriverManagerDataSource source = (DriverManagerDataSource) jdbcTemplate.getDataSource();
        source.setUrl(config.getUrl() + dbName);
        jdbcTemplate.setDataSource(source);
        String querySQL = "SELECT column_name FROM table_name WHERE column_name = ?";
        boolean found = jdbcTemplate.query(querySQL, new Object[]{value}, (ResultSetExtractor<Boolean>) rs -> rs.next());
        if (found) {
            log.info("Значение " + value + " найдено в базе данных " + dbName + " выполнил поток № " + threadName);
        } else {
            log.info("Значение " + value + " не найдено в базе данных " + dbName + " поток № " + threadName);
        }
    }
}
