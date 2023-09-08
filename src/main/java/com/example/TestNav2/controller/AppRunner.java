package com.example.TestNav2.controller;

import com.example.TestNav2.DAO.DatabaseDAO;
import com.example.TestNav2.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
    private final DatabaseDAO databaseDAO;
    private final QueryService queryService;

    @Autowired
    public AppRunner(DatabaseDAO databaseDAO, QueryService queryService) {
        this.databaseDAO = databaseDAO;
        this.queryService = queryService;
    }

    @Override
    public void run(String... args) {
        // Создаем и запускаем три БД
        for (int i = 1; i <= 3; i++) {
            databaseDAO.createDatabase("db_" + i);
            databaseDAO.createTable("db_" + i);
        }

        // Создаем три потока для создания БД
        for (int i = 1; i <= 3; i++) {
            final int finalI = i + 3;
            new Thread(() -> databaseDAO.createDatabaseFromTemplate("db_" + finalI, "db_1")).start();
        }

        // Создаем потоки для выполнения запросов
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> queryService.performQueries(String.valueOf(finalI + 1))).start();
        }

    }
}
