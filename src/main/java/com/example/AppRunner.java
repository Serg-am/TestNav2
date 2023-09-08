package com.example;

import com.example.repositories.DatabaseRepository;
import com.example.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class AppRunner implements CommandLineRunner {
    private final DatabaseRepository databaseRepository;
    private final QueryService queryService;

    @Autowired
    public AppRunner(DatabaseRepository databaseRepository, QueryService queryService) {
        this.databaseRepository = databaseRepository;
        this.queryService = queryService;
    }

    @Override
    public void run(String... args) {
        // Создаем и запускаем три БД
        for (int i = 1; i <= 3; i++) {
            databaseRepository.createDatabase("db_" + i);
            databaseRepository.createTable("db_" + i);
        }

        // Создаем три потока для создания БД
        CountDownLatch databaseCreationLatch = new CountDownLatch(3);
        for (int i = 1; i <= 3; i++) {
            final int finalI = i + 3;
            new Thread(() -> {
                databaseRepository.createDatabaseFromTemplate("db_" + finalI, "db_1");
                databaseCreationLatch.countDown(); // Уменьшаем счетчик при завершении создания БД
            }).start();
        }

        try {
            // Ждем, пока все БД будут созданы
            databaseCreationLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Создаем потоки для выполнения запросов
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            new Thread(() -> queryService.performQueries(String.valueOf(finalI + 1))).start();
        }
    }
}