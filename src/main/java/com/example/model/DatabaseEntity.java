package com.example.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "pg_database")
public class DatabaseEntity {
    @Id
    @Column(name = "oid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "datname")
    private String databaseName;
}