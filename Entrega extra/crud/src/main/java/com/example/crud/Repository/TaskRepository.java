package com.example.crud.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.crud.TaskModel;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {

    @Query("SELECT t FROM TaskModel t WHERE t.userId = :userId")
    List<TaskModel> getUserTasks(@Param("userId") String userId);
}