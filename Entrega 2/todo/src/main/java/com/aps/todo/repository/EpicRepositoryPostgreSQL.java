package com.aps.todo.repository;


import org.springframework.stereotype.Repository;

@Repository
public interface EpicRepositoryPostgreSQL extends EpicRepository {
    // You can add custom query methods specific to PostgreSQL, if needed
}