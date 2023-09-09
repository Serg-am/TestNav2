package com.example.repositories;

import com.example.model.DatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgreSQLRepository extends JpaRepository<DatabaseEntity, Integer> {

}
