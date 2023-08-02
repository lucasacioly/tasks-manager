package com.aps.todo.daos;

import com.aps.todo.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<TaskModel, Long> {

    @Query("SELECT t FROM TaskModel t WHERE t.userId = :userId")
    List<TaskModel> getUserTasks(@Param("userId") String userId);
}